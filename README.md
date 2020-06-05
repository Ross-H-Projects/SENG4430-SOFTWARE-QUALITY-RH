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

Output Explanation:

```
"Fan In": {
    "mode": "method",
    "result":{
        "FanIn1": {
            "FanIn1()": 2,
            "FanIn1_1()": 1
        }
    }
}
```
The above snippet shows an example output for fan in in method mode. The result is an object containing class names as keys.
The value of each class is another object, with method names (and parameter definitions) which contain the corresponding fan in value

```
"Fan In": {
    "mode": "module",
    "result":{
        "FanIn1": 2,
        "FanIn3": 0,
        "FanIn2": 2
    }
}
```
The above snippet shows fan in run in module mode. In this mode, the result object contains class names as keys again,
but instead of another object, their values are just the fan in score of the entire class

*NOTE:* Running method mode and module mode over the same code will not produce the same totals.
As in, summing the score for each method in a class in method mode will not necessarily equal the score given to a class in module mode.
This is due to the way they are defined as stated above

### Fan Out

Options:

`-module`: Toggles module mode. Module mode produces a result per class and only includes fan outs pointing to non caller class.
            When not provided, method mode is used. Method mode produces a score per method and counts fan outs pointing to all non caller methods.
            **Default:** false

`-min`: Set an integer value to only show classes/methods with a score greater than or equal to the value. **Default**: 5

Example usage:

`-m "fan_out -module -min 10"`

Output Explanation:
```
"Fan Out": {
    "mode": "method",
    "result":{
        "FanOut1": {
            "fanOut1_1()": 3,
            "FanOut1()": 0
        }
    }
}
```

The above snippet shows an example output for fan out in method mode. The result is an object containing class names as keys.
The value of each class is another object, with method names (and parameter definitions) which contain the corresponding fan out value

```
"Fan Out": {
    "mode": "module",
    "result":{
        "FanOut1": 1,
        "FanOut2": 1,
        "FanOut3": 2,
        "FanOut4": 1,
    }
}
```

The above snippet shows fan out run in module mode. In this mode, the result object contains class names as keys again,
but instead of another object, their values are just the fan out score of the entire class

*NOTE:* Running method mode and module mode over the same code will not produce the same totals.
As in, summing the score for each method in a class in method mode will not necessarily equal the score given to a class in module mode.
This is due to the way they are defined as stated above

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

    {'Coupling': {
        'Coupling Total': 64,
        'Weighted Graph': {
            'A3': {
                'Page': 2,
                'Process': 2,
                'MemoryManagementUnit': 0,
                'CPU': 6
            },
            ...

The running the coupling metric returns a json that features two main elements. C

'Coupling Total': counts the number of instances of paramater coupling (implicit constructor, explicit method calls through constructed object reference, explicit static calls) between classes taken as pairs.
One classes is said to be coupled with another on a unidirectional basis; parent/child relationship. If class A is coupled with class B it does not entail that Class B is coupled with Class A.

 'Weigheted Graph': For each class in analysed program shows if it is coupled with any of the other classes if the corresponding class possesses value greater than zero, and shows the amount of times the class couples with the corresponding class.

Example usage:

`-m "coupling"`

Option:

'-min': Allows user to specify a lower threshold for the class-to-class coupling values in the weighted graph portion of the json output.
In the instance of very large programs this can help to locate points of copious coupling by eliminating class-to-class non-coupling values (E.g. 'MemoryManagementUnit': 0,) and coupling values below the threshold from display.

Example usage:

`-m "coupling -min 3"`

### Halstead Complexity

Output Explanation:

    {'Halstead Complexity': {
        'Halstead Numbers': {
            'number of distinct operators (n1)': 10,
            'number of distinct operands (n2)': 14,
            'total number of operators (N1)': 12,
            'total number of operands (N2)': 22,
            },
        'Halstead Complexity Measures': {
            'Diffulty': 5.0,
            'Volume': 155.88872502451935,
            'Time required to program': 43.30242361792204(sec),
            'Effort': 779.4436251225967,
            'Program vocabulary': 24,
            'Program length': 34,
            'Estimated program length': 86.52224985768007,
            'Delivered bugs': 0.02823158219728793,
            },
        }
    }

Halstead complexity measures are calculated from four counts: number of distinct operators, distinct operands, total number of operators, and total number of operands.
All 37 ASCII operators in the java language are counted except "=".

Example usage:

`-m "halstead_complexity"`