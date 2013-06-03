JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	stocktracker/server/ServerDriver.java \
	stocktracker/client/AdminDriver.java \
	stocktracker/client/UserDriver.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) -f stocktracker/server/*.class
	$(RM) -f stocktracker/client/*.class
	$(RM) -f stocktracker/client/protocol/*.class
	$(RM) -f stocktracker/api/*.class
