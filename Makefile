# Clear defaults
.SUFFIXES: .java .class

# Input args
JARGS =


# Classes
JMAIN = Main
CLASS_NAMES = \
	ui.LookAndFeelDemo\
	$(JMAIN)\



# Folder names
BIN=./bin
SOURCE=./src
LIB=./lib

# Java configuration
CLASSPATH = $(BIN):$(LIB)
JFLAGS = -classpath $(CLASSPATH) -Djava.util.logging.config.file=log.properties
JCFLAGS = -classpath $(CLASSPATH) -d $(BIN)
# JCFLAGS = -g $(JCFLAGS)		# -g is for debugging
JC = javac
JV = java

# Shortcuts? More targets?
default: classes

# Rule to make .class files from .java files (only matches if .java file exists)
$(BIN)/%.class: $(SOURCE)/%.java
	$(JC) $(JCFLAGS) $<

# Make the bin directory if it doesn't exist
$(BIN)/:
	mkdir -p $(BIN)


classes: $(BIN)/ $(addprefix $(BIN)/, $(addsuffix .class, $(subst .,/,$(CLASS_NAMES))))

.PHONY: clean
clean:
	find $(BIN) -name "*.class" -print0 | xargs -0 rm -rf
	# $(RM) bin/*.class

remake: clean default

# First compile, then run immediately
run: classes
	$(JV) $(JFLAGS) $(JMAIN) $(JARGS)
