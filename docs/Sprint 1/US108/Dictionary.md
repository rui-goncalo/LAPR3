#Dictionary

Here you can find the most relevant details about our database such as the definition of some attributes and their respective meaning.

###Ship Table
- MMSI: it's an unique identifier code with 9 digits that represents the ship.
- IMO: it's an unique identifier number with 7 digits and doesn't change when transferring the ship's registration to another country.
- Vessel: it's the ship type.
- Draft: it's the vertical distance between the waterline and the bottom of the ship's hull.
- Transceiver_class: it's the class to transceiver used when sending data.

###Container Table
- Gross: it's the total internal volume of cargo.
- Iso_code: it's a universal way to show the dimensions of a container.

###Message Table
- SOG: Speed Over Ground is the speed of the vessel in one hour with respect to the land. 
- COG: Course Over Ground is the actual direction of progress of a vessel.
- Heading: it's the direction in which the ship's nose is pointed.

We assume that the others tables and respective attributes are well-named and explicit in their meaning.