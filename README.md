# leftRecursionEliminationAlgorithm
Implementing the context-free grammar (CFG) left-recursion elimination algorithm which takes an input string encoding a CFG and returns a string encoding an equivalent CFG which is not left-recursive. 
# How to use
You need to pass to LRE() function a semi-colon separated sequence of items for instance, input format: "S, ScT, T; T, aSb, iaLb, i; L, SdL, S"
output format: "S, TS′; S′, cTS′, ; T, aSb, iaLb, i; L, aSbS′dL, iaLbS′dL, iS′dL, aSbS′, iaLbS′, iS′".

