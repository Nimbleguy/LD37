JAR := compiled.jar# Your jar file, with the .jar.
ARGS := # Arguments for your jar file when running.
PROP :=
SRCDIR := src# Directory where your .java files are. No trailing /.
BINDIR := bin# Directory where your .class files should be. No trailing /.
LIBDIR := lib# Where you want your libraries. No trailing /.
JARDIR := natives
MANIFEST := $(SRCDIR)/MANIFEST.MF# Your manifest file.

# Don't change stuff after here.
LIBS := libs.conf
JFILE := $(shell find $(SRCDIR) -name "*.java")
CFILE := $(patsubst $(SRCDIR)/%.java,$(BINDIR)/%.class,$(JFILE))
EMPTY :=
SPACE := $(EMPTY) $(EMPTY)
SEP := $$
ARTIFACTS := $(patsubst %,$(LIBDIR)/%,$(shell cat $(LIBS)))



all : build run

build : $(JAR)

dep : $(ARTIFACTS)

$(SRCDIR) :
	-mkdir $(SRCDIR)

$(BINDIR) :
	-mkdir $(BINDIR)

$(LIBDIR) :
	-mkdir $(LIBDIR)

$(ARTIFACTS) : $(LIBDIR) ivysettings.xml ivy.jar $(LIBS)
	if [ ! -e "$@" ]; then java -jar ivy.jar -retrieve "$(LIBDIR)/[artifact](-[revision])(-[classifier]).[ext]" -dependency $(subst $(SEP),$(SPACE),$(patsubst $(LIBDIR)/%,%,$@)) -settings ivysettings.xml && touch $@; fi

ivy.jar :
	wget http://archive.apache.org/dist/ant/ivy/2.4.0/apache-ivy-2.4.0-bin.zip
	unzip apache-ivy-2.4.0-bin.zip
	mv apache-ivy-2.4.0/ivy-2.4.0.jar ./ivy.jar
	rm -rf apache-ivy-2.4.0 apache-ivy-2.4.0-bin.zip

$(JAR) : $(ARTIFACTS) $(BINDIR) $(CFILE) $(MANIFEST)
	cp $(MANIFEST) $(BINDIR)/manifest
	truncate -s-1 $(BINDIR)/manifest
	printf "Class-Path: $(subst $(SPACE),$(SPACE)\n$(SPACE),$(wildcard $(LIBDIR)/*.jar))$(subst $(SPACE),$(SPACE)\n$(SPACE),$(wildcard $(JARDIR)/*.jar))\n" >> $(BINDIR)/manifest
	cd $(BINDIR) && jar cvmf manifest $(JAR) * && mv $(JAR) ../$(JAR)

$(BINDIR)/%.class : $(SRCDIR)/%.java $(ARTIFACTS)
	javac -g -d $(BINDIR) -cp ".:$(LIBDIR)/*:$(BINDIR):$(SRCDIR)" $<

run : $(JAR)
	java $(PROP) -jar $(JAR) $(ARGS)

debug : build
	java -Xdebug -Xnoagent -Djava.compiler=NONE  -Xrunjdwp:transport=dt_socket,server=y,address=8888,suspend=y $(PROP) -jar $(JAR) $(ARGS)

jdb :
	jdb -attach localhost:8888

clean :
	-rm $(JAR) ivy.jar
	-rm -r $(BINDIR) $(LIBDIR)

.PHONY : all build dep dirs run debug jdb clean

