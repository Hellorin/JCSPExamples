# JCSPExamples

As part of my bachelor degree, I learned how to use the [JCSP library](https://github.com/codehaus/jcsp) and compared the mechanisms offered with the highly performant, scalable and concurrent language called Erlang. 

The files has been then updated and added in this Github showing uses of this library in very simples scholar examples.

There is two examples in this repository for now:
* Example with a measuring device that reads all sensors when data are ready
    * Synchronisation barriers usage
    * Can be found in the folder synchronisationBarrier
* Example of communication channels and timeout
    * Using the mechanisms of channel poisoning as well
    * Can be found in the folder altingPoisonChannel

Each of these folders contain an ant build file:
* First move to the approriate folder (*altingPoisonChannel* or *synchronisationBarrier*) with **cd**
* Use then the following command **ant clean run**
