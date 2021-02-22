ifeq ($(origin JAVA_HOME), undefined)
  JAVA_HOME=/usr
endif

ifneq (,$(findstring CYGWIN,$(shell uname -s)))
  JAVA_HOME := `cygpath -up "$(JAVA_HOME)"`
  COLON=\;
else
  COLON=:
endif

SRCS=$(wildcard src/*.java)

vectorview.jar vectorview.jar.pack.gz: $(SRCS) manifest.txt NetLogo.jar VectorGraphics2D-0.9.3.jar 
	mkdir -p classes
	$(JAVA_HOME)/bin/javac -g -encoding us-ascii -source 1.8 -target 1.8 -classpath NetLogo.jar$(COLON)VectorGraphics2D-0.9.3.jar -d classes $(SRCS)
	jar cmf manifest.txt vectorview.jar -C classes .
	pack200 --modification-time=latest --effort=9 --strip-debug --no-keep-file-order --unknown-attribute=strip vectorview.jar.pack.gz vectorview.jar

vectorview.zip: vectorview.jar
	rm -rf vectorview
	mkdir vectorview
	cp -rp vectorview.jar vectorview.jar.pack.gz README.md Makefile src manifest.txt vectorview
	zip -rv vectorview.zip vectorview
	rm -rf vectorview
