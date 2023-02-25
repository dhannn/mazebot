JCC=javac

SRC_PATH=src
SRC:=$(wildcard $(SRC_PATH)/**.java)

LIB_PATH=lib
LIBS := $(wildcard $(LIB_PATH)/**/*.jar)
OUT_PATH=out

all:
# javac -classpath src *
# $(shell echo $(SRC))
	javac --module-path lib/javafx/lib --add-modules javafx.controls,javafx.fxml -d $(OUT_PATH) -sourcepath $(SRC_PATH) $(SRC_PATH)/*.java
