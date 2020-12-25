# Installation with maven

## Maven repository

```xml
<repository>
    <id>jeasyrules-mvn-repo</id>
    <url>https://raw.github.com/jeasyrules/jeasyrules-core/mvn-repo/</url>
    <snapshots>
        <enabled>true</enabled>
        <updatePolicy>always</updatePolicy>
    </snapshots>
</repository>
```

## Maven dependency

```xml
<dependency>
    <groupId>jeasyrules</groupId>
    <artifactId>jeasyrules-core</artifactId>
    <version>${jeasyrules-core.version}</version>
</dependency>
```

# How to use

##  The CSV format of file

A CSV file can contains 3 types of columns:
* The predicates (which begins with P_) with 3 possible values:
    + 1 : true
    + 0 : false
    + empty: to ignore
* The decisions (which begins with D_) : returned values from a set of predicates
* The validation rules (which begins with V_) : rules which take a value object and ruleStorage as parameter and which return true or false

Example of CSV file:

```
P_USERCASE1;P_USERCASE2;P_COND;D_MSG;V_RULE
1;0;1;MSG01;Rule01
1;;0;MSG02;Rule01
1;0;1;MSG03;Rule02
```

## Loading CSV file as a decision table

```java
import java.io.InputStream;

import org.jeasyrules.core.decisiontable.impl.AbstractCSVDecisionLoader;

/**
 * Example of CSV decision table implementation.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class ExampleDecisionTableLoader extends AbstractCSVDecisionLoader<ExampleVO> {
    /**
    * {@inheritDoc}
    */
    @Override
    public void init() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("example.csv");
        this.load(in);
    }
}
```

Note: by default, the CSV row separator is `;` but you can specify another with an optional second parameter of `load` method.

## Getting decisions from predicates

```java
// This is an example of initializing a data table loader, but it's better to use a factory or a bean singleton with an IoC framework
dtLoader = new ExampleDecisionTableLoader();

// ...

Predicates predicates = Predicates.newInstance().addFalse("COND").addTrue("USERCASE1", "USERCASE2");
List<DecisionResult> decisions = dtLoader.getDecisionTable().getDecisions(predicates, exampleVO, null);
```
