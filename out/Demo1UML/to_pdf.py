from svglib.svglib import svg2rlg

from reportlab.graphics import renderPDF

drawing = svg2rlg("Demo1UML.svg")
renderPDF.drawToFile(drawing, "Demo1UML.pdf")
