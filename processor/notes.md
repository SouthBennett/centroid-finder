ImageSummaryApp:
- Must have three arguments: The path to the image(file), Target hex color(color of salmander), target threshold for binarization
- three argument data types: path = String, color = String, and threshold = number;
## Flow for main
- grab arguments
- must be 3
-  file path = args[0];
- color = args[1];
- try to grab threshold and parse into args[2];
- try to read file, error if not
- try to parse color string into 24 bit color with new color variable
- makes new instance of ColorDistanceFinder (This is used to find color differnce between pixels and theshold?)
- makes new BufferedImag( this is where an image gets binarization and turns pixels into 1's, and 0's?)
- finds where the image is, binarizes it and turns it into a 2d array
- BufferedImage binaryImage turns our binaryArray into a binary imagae
- saves the binarized image in the format of ".png"
- creates an ImageGroupFinder using BinarizinGImageGroupFinder and passes in binarizer and DfsBinaryGroupFinder();
- calls groupFinder.findConnectedGroups(inputImage)
- returns a List of type Group
- loops through each Group and writes it to "groups.csv" 