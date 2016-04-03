:@author Mugunthan

call setenv.bat

set CLASSPATH=.;classes;%PATH%

@echo CLASSPATH : %CLASSPATH%

dir .\src\*.java /s /B> sources.txt
javac -d .\classes @sources.txt -verbose

@echo Compilation of java file is completed...