# JCSPExamples

As part of my bachelor degree, I learned to use the [JCSP library](https://github.com/codehaus/jcsp) and compared the mechanisms offered with the highly performant, scalable and concurrent language called Erlang. 

The files has been then updated and added in this Github showing uses of this library in very simples scholar examples.

There is three examples in this repository for now:
* Example with actual readers and writers of a book (the shared resource):
    * Readers-Writers problem without priorities
    * Can be found in the package ch.hellorin.jcsp.readersWriters
* Example with a measuring device that reads all sensors when data are ready
    * Synchronisation barriers usage
    * Can be found in the package ch.hellorin.jcsp.synchronisationBarrier
* Example of communication channels and timeout
    * Using the mechanisms of channel poisoning as well
    * Can be found in the package ch.hellorin.jcsp.altingPoisonChannel
    
More example are to be added in the future
