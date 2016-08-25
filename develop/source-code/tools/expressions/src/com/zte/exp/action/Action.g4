grammar Action;
import Mos_exp;


action: 'tree('para')' # treeAct
    | 'select('ne','json')' # select
    | 'commit('ne','json')' # commit
    | 'onSelect('init')' # onSelect
    | 'version('ne')' # ver;

ne: 'ne('dnexp')';

json: 'json('jsonObject')';
jsonObject: ALL;
ALL: '{'.+'}';


para: exp(','exp)+;

exp: name ':' expvalue;
expvalue: init|value;
