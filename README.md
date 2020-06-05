# SENG4430-SOFTWARE-QUALITY

## Basic Usage

Download and install maven

Run ```mvn clean compile assembly:single``` to download dependencies and build the application.

Then run ```java -jar ./target/Group3.jar```

Command line arguments are required. The input path can point to a single file or an entire project.

The basic structure is

`<input-path> [-m "<metric> [<metric-flag> ...]" ...]`

An `-o` flag has also been created for output but there is currently only cmd available so that is default

The main entry point is `./src/main/java/group3/App.java`

## Test Usage

run ```java group3.TestRunner```


## Metrics

### Fan In

Options:

`-module`: Toggles module mode. Module mode produces a result per class and only includes fan ins originating from the non callee class.
            When not provided, method mode is used. Method mode produces a score per method and counts fan ins coming from all non callee methods for each method.
            **Default:** false

`-max`: Set an integer value to only show classes/methods with a score less than or equal to the value. **Default**: 1

Example usage:

`-m "fan_in -module -max 2"`

### Fan Out

Options:

`-module`: Toggles module mode. Module mode produces a result per class and only includes fan outs pointing to non caller class.
            When not provided, method mode is used. Method mode produces a score per method and counts fan outs pointing to all non caller methods.
            **Default:** false

`-min`: Set an integer value to only show classes/methods with a score greater than or equal to the value. **Default**: 5

Example usage:

`-m "fan_out -module -min 10"`

### Average Length of Identifiers

Output Explanation:

`Class Averages`: Outputs the average length of identifiers for each class in the project.

`Noteworrthy Identifiers`: Outputs the noteworthy identifiers decided by the cutoff point option, as explained below.

Options:

`-cutoff`: Set an integer value to only show specific identifiers in which the identifiers' length is less than or equal to the integer value.
            **Default**: 4

Example usage:

`-m "length_of_identifiers -cutoff 2"`

### Fog Index

Output Explanation:

`Method Scores`: For each class; Outputs the fog index score for every method in that class. The fog index score is based on the comments attached to the method.

Example usage:

`-m "fog_index"`


### Depth Of Inheritance

Output Explanation:

`score`: the deepest inheritance chain, e.g. if we have a situation with 3 java classes: A, B, and C; where C inherits from B, and B inherits from A. We will have get a score of 2.

`chains`: This shows the actual inheritance chains, for the example described above this would should:

    'chains': [
        ['C', 'B', 'A'],
        ['B', 'A'],
        ['A']
    ]

Example usage:
`-m "inheritance_depth"`


### Lack Of Cohesion

Output Explanation

    {'Lack Of Cohesion': [
        {
            'Class Name': 'A',
            'Amount of Methods in class': '3',
            'Coherence Score': '0',
            'Coherence Ratio': '1.00'
        },
        {
            'Class Name': 'B',
            'Amount of Methods in class': '3',
            'Coherence Score': '0',
            'Coherence Ratio': '0.67'
        }
    ]}

Each object entry in the array for 'Lack Of Cohesion' shows the cohesion information for a class.

`Coherence Score`: the cohesion calculation (as per this spec: https://help.semmle.com/wiki/pages/viewpage.action?pageId=29394037) for the respective class.

`Coherence Ratio` The result of A/B. Where A is the # of distinct method pairs with atleast one common field member access
and B  is the # of distinct methods pairs.

Example usage:
`-m "cohesion_score"`

### Coupling

Output Explanation:

`Method Scores`: For each class; Outputs the fog index score for every method in that class. The fog index score is based on the comments attached to the method.

Example usage:

`-m "fog_index"`
