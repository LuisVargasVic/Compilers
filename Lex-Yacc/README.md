Run in terminal
$ lex lex-yacc.l
$ yacc -d lex-yacc.y
$ cc lex.yy.c y.tab.c -o lex-yacc
$ ./lex-yacc
//example 
entero numero;
numero = 21;
