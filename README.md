# ontoTaxoSeeker
Search for taxonomy paths in texts. The taxonomy has to be the produced by [ieee2owl](https://github.com/guillem72/ieee2owl).

## Introduction
This piece of sotfware produces the list of paths from an ontology represents a taxonomy. The taxonomy used is called 
IEEE computers taxonomy made by [ieee2owl](https://github.com/guillem72/ieee2owl) and, 
due to the license of the IEEE taxonomy, has to be construct by every user. 

The [IEEE taxonomy](http://www.ieee.org/documents/taxonomy_v101.pdf) is a good language resource for engineering.
The intention of use of ontoTaxoSeeker is for anotate docs to enrich its information.

## Installation
TODO
### As framework
It is needed:

- FreeLing in java. 
- Maven
- Apache jena
- Apache commons-lang3
- [com.glluch.utils](https://github.com/guillem72/Utils)

## Use
See the method findPaths in Main.java as an example. It takes a text, process it, and show a list of paths.

##JavaDocs
[JavaDocs of ontoTaxoSeeker](https://guillem72.github.io/ontoTaxoSeeker/)