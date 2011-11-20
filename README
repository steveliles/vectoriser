## Toy project for vectorising bitmaps

The idea has two parts:

1. Reduce the number of colours by various image processing techniques to 
   produce a bitmap that looks something like the vector image will eventually look.
   
2. Trace the reduced colour image, creating an SVG document that describes it

So far there are two (java) packages in the main source tree: The `pre-process` package
contains work in progress on colour reduction and clustering; the `vectorize` package
contains (again) work in progress on tracing the reduced-colour image to an SVG.

The test tree contains a number of unit tests that are driving the project along, and
some test images. The status at time of writing is that images can be vectorised and
the results do resemble the input bitmap, but are somewhat less than perfect and are 
quite enormous (the 600x600 "pre-processed-duck.jpg" is 76kb, but produces a 12Mb svg file!)

