# trade validator
whole project right now contains only api module and integration module. there is no core api logic as validation is part of decoding from public API model. 

# not happy about
* there is not enough test cases around validators - no enough time
* the way validators works with child validators - is fine for this small toy project
* error message assertion in IT test - is fine for this small toy project
* there are certain things I didn't implemented because I didnt understand the requirements but passing information around was taking too much time.
* some validation is done on bean level which test integrity of the data rather then business validation.

# task description
There is a demand to extend the STP flow with trade validation service for the FOREX transactions – 
FX Spot,
Forward, 
Options. 

In addition there should be a small client provided. 
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



