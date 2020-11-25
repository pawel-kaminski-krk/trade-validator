# video-rental

whole project is divided into 2 main modules

1. api + public api-model - rest api functionality based on spring boot.
1. core - rental functionality

additionally there is also integration test module that verifies everything works as expected from API point of view.

# assumptions
* Any customer ref is a valid customer - there is no validation or call to customer system
* Collecting and processing the fee should be done by separate system, this rental system only keeps track of what was calculated and how much should be collected
* there is no local, durable db, repositories are backed by HashMaps, so rental data live as long service lives
* the human nature of the rental process makes this application very synchronous, you cannot return same video tape twice or from two concurrent places - so there should be no races.
* I didn't create any client library that could be share with other systems.
* there is static configuration that is loaded on app load - refer to `casumo-video-rental\video-rental-core\src\main\resources\config.yaml`. This config also contains movies list for simplicity.
* I didn't cover with unit-tests every inch of application - I rather focused on functional tests that prove that given scenarios work as expected and
unit-tested those classes that were critical for application to work properly.
* I dont keep track of how many movies were already rented, so any movie is available to any customer even if other customer just rented it
* customer cannot physically return something that he didn't rent or cannot return same movie twice. application does not guard against such cases.
* any amount is kept in default currency which is `SEK` but there is service placeholder that could be reimplemented to support any currency.

# improvements
This application is very simple and I didn't want to make it too complex. `RestService` class acts as a facade for multiple functionalities
* creating rental transaction
  * creating the rental event
  * calculating the fee for rental
  * adding bonus points
* canceling existing rental transaction
  * creating final returning event
* return all or some rented movies to
  * creating movie returning event - customer can return any number of rented previously tapes, if returns unknown movie - I simply ignore it
  * calculating additional fees for being late
  
and it would be wise to refactor it into some system of dispatching and listening to the custom events.
Said that I designed models to be fully immutable which will allows to divide and scale application in future.
`com.ffb.tradevalidator.model.RentalTransaction`, `com.ffb.tradevalidator.model.ReturnTransaction` and `com.ffb.tradevalidator.model.RentalCharge` work independently from each other,
making perfect candidates for future event sourcing

# task description
There is a demand to extend the STP flow with trade validation service for the FOREX transactions – FX Spot,
Forward, Options. In addition there should be a small client provided. 
Validation results shall be displayed in the console, logs or displayed in the GUI with the information about the failure.
Technical requirements:
1. The service shall expose a REST interface consuming trades in JSON format and returning validation result to
the client
2. Service shall be flexible to extend the validation logic in terms of:
- new business validation rules
- new products to be supported
3. Service should support graceful shutdown
Business requirements:
1. The following basic validation rules shall be implemented:
All types of trades:
- value date cannot be before trade date
- value date cannot fall on weekend or non-working day for currency
- if the counterparty is one of the supported ones
- validate currencies if they are valid ISO codes (ISO 4217)
Spot, Forward transactions:
- validate the value date against the product type
Options specific:
- the style can be either American or European
- American option style will have in addition the excerciseStartDate, which has to be after the trade date but
before the expiry date
- expiry date and premium date shall be before delivery date
2. The validation response should include information about errors detected in the trade (in case multiple are
detected, all of them should be returned) and in case of bulk validation additional linkage between the error
and the actual trade should be returned
Assumptions:
1. Current date is 09.10.2020
2. Supported counterparties (customers) are : YODA1, YODA2
3. Only one legal entity is used: UBS AG



