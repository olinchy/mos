grammar Mos_exp;

init
:
    '(' boolean_expr ')'
    | boolean_expr
;
dbexp
:
    name
;

dnexp
:
    dnsec
    | '('dnsec')'
;
dnsec: dnterm # oneDNTerm
    | dnterm dnterm # twoDNTerm; 


// a.dn = mo.dn/rack/1/shelf/1/E1Port/*/${boardtype}/board.no
dnterm: 
    moexp
    | localexp
    | dnele
;

dnele: (element element)+ # dnElements
    | SLASH # rootDN
    | SLASH wildcard # wildDN
;
element: SLASH(moexp | localexp) # slashExp
    | SLASH (name | index | dbString | neid | wildcard) # slashValue
;
neid: 'mw.nr'INT'='index | 'mw.nr8000tr='index;

wildcard: '*';

SLASH: '/';

index
:
    INT
;

comparasion
:
    extendedExp EQUAL extendedExp # equal
    | extendedExp BIGGER extendedExp # bigger
    | extendedExp SMALLER extendedExp # smaller
    | extendedExp IN array # in
    | extendedExp CONTAINS extendedExp # contains
    | extendedExp NOTEQUAL extendedExp # notEqual
    | extendedExp BIGGEROREQUAL extendedExp # bOrE
    | extendedExp SMALLEROREQUAL extendedExp # sOrE
    // mo.xxx like ${dn}/E1Port/*
    // mo.xxx like /Shelf/1/Board/.*/E1Port/.*
    | extendedExp LIKE extendedExp # like
;
LIKE: 'like' | 'LIKE';

array: '['arrayexp']';
arrayexp: value(',' value)*;

boolean_expr
:
    boolean_term # boolTerm
    | boolean_term (OR boolean_term)+ # or
;

boolean_term
:
    boolean_element # boolElement
    | boolean_element (AND boolean_element)+ # and
;

boolean_element
:
    'true' # trueValue
    | 'false' # falseValue
    | comparasion # readCompare
    | '(' boolean_expr ')'  # boolExpr
    | NOT '(' boolean_expr ')' # not
;

extendedExp
:
    moexp
    | localexp
    | dnexp
    | dbexp
    | calculateExp
    | value
;


calculateExp: calcAddEle # calcaddT
    | calcAddEle ADD calcAddEle # add
    | calcAddEle MINUS calcAddEle # minus; 

MINUS: '-';
ADD: '+';

calcAddEle: calcMultiEle # calcmulti
    | calcMultiEle MULTI calcMultiEle # multi
    | calcMultiEle DIVID calcMultiEle # divid;
DIVID: 'div';

MULTI: '*';

calcMultiEle: calcTerm # calcT
    | calcTerm LEFT calcTerm #left
    | calcTerm RIGHT calcTerm #right
    | calcTerm AMPERSAND calcTerm # ampersand
    | calcTerm BAR calcTerm # bar;

BAR: '|';

AMPERSAND: '&';

RIGHT: '>>';

LEFT: '<<';

calcTerm: intValue
    | '('calculateExp')'
    | dbexp;

localexp
:
    '${' name '}'
;

moexp
:
    className '.' propertyName
;

propertyName: TEXT;

className: TEXT;



value
:
    intValue
    | pureString
    | dbString
;
dbString: STRING;

pureString: TEXT;

intValue: octValue
    | hexValue;

hexValue: HEX;

octValue: INT;

HEX: '0x'[0-9A-Fa-f]+;


STRING: '\'' [\ A-Za-z0-9=\.\*/\-_\(\)]+ '\'';


INT
:
    [0-9]+
;

NOTEQUAL
:
    '!='
;

CONTAINS
:
    'contains'
;

IN
:
    'in'
    | 'IN'
;

NOT
:
    'not'
    | '!'
;

OR
:
    'or'
    | 'OR'
;

AND
:
    'and'
    | 'AND'
;

SMALLER
:
    '<'
;

BIGGER
:
    '>'
;

EQUAL
:
    '='
;

SMALLEROREQUAL
:
    '<='
;

BIGGEROREQUAL
:
    '>='
;

name
:
    TEXT
;

TEXT
:
    [A-Za-z0-9_]+
;

WS
:
    [ \t\r\n]+ -> skip
;
