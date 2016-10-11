# MOS

Data provider

## Installation

### Step #1 copy lib to maven repository
```
mkdir -p ~/.m2/repository/com/sleepycat/je/6.3.8/
cp ../../lib/* ~/.m2/repository/com/sleepycat/je/6.3.8/
```

### Step #2 build
mvn clean install -DskipTests

### Step #3 start the web service
cd simulator/mos_sdn/run

chmod a+x run.sh
./run.sh

### Step #4 start cli in another terminal
python mos/develop/cli/main.py http://localhost:8282/mos

### Step #5 build the data in need
in cli you can use 'TAB' key to know the next step

command like this

add /NE/[NENAME];

add /NE/[NENAME]/AirInterface/LP-MWPS-ifIndex1 txFrequency [valueOfTx] rxFrequency [valueOfRx];

add /NE/[NENAME]/AirInterface/LP-MWPS-ifIndex2 txFrequency [valueOfTx] rxFrequency [valueOfRx];


