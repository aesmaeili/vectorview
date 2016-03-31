# NetLogo vectorview extension

This package contains the NetLogo vectorview extension. This extension is built to be used for exporting a Netlogo model view to vector image formats, EPS, PDF, and SVG.

## Using

Like all Netlogo extensions, vectorview is needed to be imported in netlogo by `extensions [vectorview]`.
The main command of this extension is `export-view-vector`. This command gets three arguments of types string, number, and string, respectively. The first argument is for specifying the intended vector format. The supported formats are EPS, PDF, and SVG. The second argument is for specifying the scale for the generated image. And finally, the name of the generated image file is specified by the third argument. The exported file is going to be placed in the same directory of the model. 

## Example

In order to export the current view of the model to EPS formated file `file1.eps` which is twice as large as the size of the model view, we write: 

`vectorview:export-view-vector "EPS" 2 "file1"`

## Building

Run `make`. 

If compilation succeeds, `vectorview.jar` will be created. Please note that the extension needs VectorGraphics2D library, which can be obtained from [here].

___

[here]: http://trac.erichseifert.de/vectorgraphics2d/
