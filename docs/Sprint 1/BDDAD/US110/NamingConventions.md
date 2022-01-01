#Naming Conventions

###Table:
- Should have a simple and direct name with only one word always that's possible: `FirstTable`.
- If we need to create a third table to accomplish the 3NF, then this table should be named: `FirstTable_SecondTable`.

###Relation:
- The relationship between tables must have a word (usually a verb) that represents its connection and helps its reading: `FirstTable -|--- has ---< SecondTable`.

###Attribute:
- Attributes must be represented with as few words as possible (separated by _) and, in case that is not possible, then abbreviations must be used.
In case this last option is used, then they must be available in the dictionary: `Attribute_with_words` or `Awalow` to represent "Attributes with a lot of words".
- Never start an attribute name with a numeric character.

###Primary Key:
- In the representation of the Primary Key, we must name the constraint as follows: `table_pk`.

###Foreign Key:
- In the representation of the Foreign Key we must name the constraint as follows: `table_fk`.

###Check Constraint
- In the representation of the Check Constraint we must name it as follows: `table_ck`.

###Not Null:
- In the representation of the Check Constraint we must name it as follows: `table_nn`.