
# jCAE
from org.jcae.mesh.amibe.ds import Mesh, Triangle
from org.jcae.mesh.amibe.algos3d import SmoothNodes3D
from org.jcae.mesh.amibe.traits import MeshTraitsBuilder
from org.jcae.mesh.xmldata import MeshReader, MeshWriter

# Java
from java.util import HashMap, Map
from java.lang import String, Class

# Python
import sys
from optparse import OptionParser

"""
Decimate a mesh.
"""

defaultAlgo = "QEMDecimateHalfEdge"
parser = OptionParser(usage="amibebatch decimate [OPTIONS] <inputDir>\n\nDecimate a mesh", prog="decimate")
parser.add_option("-t", "--tolerance", metavar="FLOAT",
                  action="store", type="float", dest="tolerance",
                  help="geometry error allowed when decimating")
parser.add_option("-f", "--freeEdgeTol", metavar="FLOAT",
                  action="store", type="float", dest="freeEdgeTol",
                  help="Decimate free edges whose length is smaller than tolerance.  -t value is used if not specified. (for LengthDecimateHalfEdge only)")
parser.add_option("-n", "--targetTriangles", metavar="NUMBER",
                  action="store", type="int", dest="targetTriangles",
                  help="stops iterations when mesh contains this number of triangles")
parser.add_option("-m", "--maxlength", metavar="FLOAT",
                  action="store", type="float", dest="maxlength",
                  help="no edges longer than this value are created")
parser.add_option("-O", "--freeEdgeOnly", action="store_true", dest="freeEdgeOnly",
                  help="removes only free edges (for LengthDecimateHalfEdge only)")
parser.add_option("-a", "--algorithm", metavar="STRING", default=defaultAlgo,
                  action="store", type="string", dest="algorithm",
		  help="decimation algorithm (default: "+defaultAlgo+")")
parser.add_option("-A", "--list-algorithm", action="store_true", dest="listAlgorithm",
                  help="lists all available decimation algorithms")
parser.add_option("-o", "--output", metavar="STRING",
                  action="store", dest="output",
                  help="writes decimated mesh into this directory")

(options, args) = parser.parse_args(args=sys.argv[1:])

if len(args) != 1:
	parser.print_usage()
	sys.exit(1)

if options.listAlgorithm:
	print "Available algorithms for decimation:" 
	print "    LengthDecimateHalfEdge" 
	print "    QEMDecimateHalfEdge" 
	sys.exit(0)

xmlDir = args[0]
outDir = options.output
if not outDir:
	outDir = xmlDir

opts = HashMap()
if options.tolerance:
	opts.put("size", str(options.tolerance))
elif options.targetTriangles:
	opts.put("maxtriangles", str(options.targetTriangles))
if options.freeEdgeOnly:
	opts.put("freeEdgeOnly", "true");
if options.freeEdgeTol:
	opts.put("freeEdgeTol", str(options.freeEdgeTol))
if options.maxlength:
	opts.put("maxlength", str(options.maxlength))

mesh = Mesh()
MeshReader.readObject3D(mesh, xmlDir)

cons = Class.forName("org.jcae.mesh.amibe.algos3d."+options.algorithm).getConstructor([ Mesh, Map ])
cons.newInstance([ mesh, opts ]).compute()

MeshWriter.writeObject3D(mesh, outDir, String())

