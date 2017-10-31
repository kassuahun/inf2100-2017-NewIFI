/**
* This scanner program implements an application that takes in an Asp program file, read line by line and store each characters 
* by creating a tokens object.
*
* @author Kassahun and Melat
* @since August 1027
*
*/

package no.uio.ifi.asp.scanner;

import no.uio.ifi.asp.main.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
     private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private int indents[] = new int[100];
	private int top = 0;
    private int numIndents = 0;
    private final int tabDist = 4;
/**
* The scanner constructor takes in a file and store in in the curFileName.
* it then creates an object of line numer reads by taking the file namne. 
* if it can not be read it will print error message
* @param String filename
* @return Nothing
*/
    public Scanner(String fileName) {
        curFileName = fileName;
        indents[0] = 0;  numIndents = 1;

        try {
            sourceFile = new LineNumberReader(
                    new InputStreamReader(
                    new FileInputStream(fileName),
                    "UTF-8"));
        } catch (IOException e) {
            scannerError("Cannot read " + fileName + "!");
        }
    }
/**
* This method is to store in a variable m is or to print out an error if there is 0 line to read or no more line.
* @param message  
* @return Nothing
*/
    private void scannerError(String message) {
        String m = "Asp scanner error";
        if (curLineNum() > 0)
            m += " on line " + curLineNum();
        m += ": " + message;

        Main.error(m);
    }
/**
* This method is to store in a variable m is or to print out an error if there is 0 line to read or no more line.
* @param message  
* @return Nothing
*/
    public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
	return curLineTokens.get(0);
    }

    public void readNextToken() {
        if (! curLineTokens.isEmpty())
            curLineTokens.remove(0);
    }

    public boolean anyEqualToken() {
        for (Token t: curLineTokens) {
            if (t.kind == equalToken) return true;
        }
	return false;
    }

    public void readNextLine() {
        curLineTokens.clear();
        String line = null;
        try {
            line = sourceFile.readLine();
                if (line == null) {
                sourceFile.close();
                sourceFile = null;
                } else {
            Main.log.noteSourceLine(curLineNum(), line);
            }
        } catch (IOException e) {
            sourceFile = null;
            scannerError("Unspecified I/O error!");
        }

	//-- Must be changed in part 1:
        if (line==null){
            addToIndents(0);
            curLineTokens.add(new Token(eofToken,curLineNum()));

        }/*else if(line.startsWith("#")){

            return;
        }*/else if("".equals(line.trim()) || line.startsWith("#")){

        return;
        }else{
                String currentLine = expandLeadingTabs(line);
                numIndents = findIndent(currentLine);
                addToIndents(numIndents);
                findToken(currentLine);
         }

        // Terminate line:
        curLineTokens.add(new Token(newLineToken,curLineNum()));

        for (Token t: curLineTokens)
            Main.log.noteToken(t);
    }

    public TokenKind tokenKindSearch(String tImage){
        for (TokenKind k: TokenKind.values()) {
            if(tImage.equals(k.image)){
                return k;
            }
        }
        return null;
    }

	private String addToIndents(int tabs){

			if(indents[top] == 0 && tabs > 0 && top == 0){
				indents[top+1] = tabs;
				top = top +1;
				curLineTokens.add(new Token(indentToken,curLineNum()));
				return "INDENT";
			} else if(indents[top] < tabs){
				indents[top+1] = tabs;
				top = top +1;
				curLineTokens.add(new Token(indentToken,curLineNum()));
				return "INDENT";
			}else if(indents[top] > tabs){
				for (int i = top; i >= 0; i--) {
					if (tabs == indents[i])
					{
						top = i;
						//curLineTokens.add(new Token(dedentToken,curLineNum()));
						return "DEDENT";
					}
					else if(tabs < indents[i]){
						top = i;
						curLineTokens.add(new Token(dedentToken,curLineNum()));
					}
					indents[i] = 0; //set the stack to original value
				}
				scannerError("Indentation Error on the ASP code1");
				return "Indentation Error on the ASP code";
			}else if(indents[top]== tabs) {
				return null;
			}
            scannerError("Indentation Error on the ASP code");
			return "Indentation Error on the ASP code";
	}

    private String findToken(String lineIn){
		int currentPos = numIndents;
		String currentToken = "";


		/*
		*starts with digit, if 111 it is int if 11.1 it is float, if 11.11.1 it is wrong and handles as 11.11 and .
		*
		*/
		while (currentPos < lineIn.length()){
    //digit
            boolean flnum = false;
			if (isDigit(lineIn.charAt(currentPos))) {
				while (currentPos < lineIn.length()){
					if (isDigit(lineIn.charAt(currentPos))) {
						currentToken = currentToken + lineIn.charAt(currentPos);
						currentPos++;
					} else if (lineIn.charAt(currentPos) == '.' && (!flnum) && isDigit(lineIn.charAt(currentPos + 1))) {
						currentToken = currentToken + lineIn.charAt(currentPos);
						flnum = true;
						currentPos++;
					}else if(lineIn.charAt(currentPos) == '.' && flnum){
						scannerError("ERROR: wrong number format: . at line  "+ curLineNum() + "position " + currentPos );
						return null;
					}
					else {
						break;
					}
				}
				if (flnum) {
					Token tokenTobe = new Token(floatToken, curLineNum());
					tokenTobe.floatLit = Double.parseDouble(currentToken);
					curLineTokens.add(tokenTobe);
				} else {
					Token tokenTobe = new Token(integerToken, curLineNum());
					tokenTobe.integerLit = Long.parseLong(currentToken);
					curLineTokens.add(tokenTobe);
				}
				currentToken = "";
			}// end of digit
    //if it is letter
			else if(isLetterAZ(lineIn.charAt(currentPos))){
				while (currentPos < lineIn.length()) {
					if (isLetterAZ(lineIn.charAt(currentPos)) || lineIn.charAt(currentPos) == '_' || isDigit(lineIn.charAt(currentPos))) {
						currentToken = currentToken + lineIn.charAt(currentPos);
						currentPos++;
					}
					else{
						break;
					}
				}
				TokenKind currentKind = tokenKindSearch(currentToken);
                Token tokenTobe;
				if (currentKind == null){
                    tokenTobe = new Token(nameToken, curLineNum());
                    tokenTobe.name = currentToken;
                }else
                {
                    tokenTobe = new Token(currentKind, curLineNum());
                }
                curLineTokens.add(tokenTobe);
                currentToken = "";


			}// end if is letter
    // is space or tab between string?
			else if(isSpace(lineIn.charAt(currentPos))){
				currentPos++;
			} //end of space
    //is "xyz"  quote
            else if(lineIn.charAt(currentPos)=='\"' || lineIn.charAt(currentPos)=='\'' ) {
				//boolean inn = true;
				boolean exit = true;
				currentPos++;
				while (currentPos < lineIn.length() && exit) {
					if (lineIn.charAt(currentPos)=='\"'||lineIn.charAt(currentPos)=='\'') {

						Token tokenTobe = new Token(stringToken, curLineNum());
						tokenTobe.stringLit = currentToken;
						curLineTokens.add(tokenTobe);
						currentToken = "";
						currentPos++;
						exit = false;
					}
					else{

						currentToken = currentToken + lineIn.charAt(currentPos);
						currentPos++;
					}
				}//end of while
				if (exit) {
                    scannerError("Error  \" do not closed line " + curLineNum() + " at " + currentPos);
                    return null;
				}

				}
    //Symbols
				else {
			    String oneSymbol= "";
                String twoSymbol= "" ;
                String threeSymbol= "";
                if((currentPos+1) <=lineIn.length()) {
                    oneSymbol = lineIn.substring(currentPos, currentPos+1);
                }

                if(currentPos+2 <=lineIn.length()) {
                     twoSymbol = lineIn.substring(currentPos, currentPos + 2);
                }
                if(currentPos+3 <=lineIn.length()) {
                    threeSymbol = lineIn.substring(currentPos, currentPos + 3);
                }
        //three symbols
                if(tokenKindSearch(threeSymbol) != null){
                    Token tokenTobe = new Token(tokenKindSearch(threeSymbol), curLineNum());
                    curLineTokens.add(tokenTobe);
                    currentPos +=3;
                }else  if(tokenKindSearch(twoSymbol) != null){
                    Token tokenTobe = new Token(tokenKindSearch(twoSymbol), curLineNum());
                    curLineTokens.add(tokenTobe);
                    currentPos += 2;
                }else  if(tokenKindSearch(oneSymbol) != null){
                    Token tokenTobe = new Token(tokenKindSearch(oneSymbol), curLineNum());
                    curLineTokens.add(tokenTobe);
                    currentPos++;
                }else{
                    scannerError("unknown Symbol =  "+ lineIn.charAt(currentPos));
                    currentPos++;
                    return null;
                    }
				}
		}//end of while
		return null;
	}

    public int curLineNum() {
		return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
		int indent = 0;
		while (indent<s.length() && s.charAt(indent)==' ') indent++;
		return indent;
    }

    private String expandLeadingTabs(String s) {
	String newS = "";
	for (int i = 0;  i < s.length();  i++) {
	    char c = s.charAt(i);
	    if (c == '\t') {
		do {
		    newS += " ";
		} while (newS.length()%tabDist != 0);
	    } else if (c == ' ') {
		newS += " ";
	    } else {
		newS += s.substring(i);
		break;
	    }
	}
	return newS;
    }


    private boolean isLetterAZ(char c) {
	return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }

    private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }

    private boolean isSpace(char c) {
		return c == ' ' || c == '\t';
	}

	private boolean isQuote(char c) {
		return c == '"';
	}


    public boolean isCompOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:

	return (k == lessToken || k == greaterToken || k == doubleEqualToken ||
			k == greaterEqualToken || k == lessEqualToken || k == notEqualToken);
    }

    public boolean isFactorPrefix() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return (k == plusToken || k==minusToken );
    }

	public boolean isPrimaryPrefix() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return (k == leftBracketToken || k==leftParToken );
	}

    public boolean isFactorOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return (k ==astToken || k==slashToken || k==percentToken || k==doubleSlashToken );
    }

    public boolean isTermOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return (k == plusToken || k==minusToken );
    }
/*
	public boolean isDigit1to9() {
		int k = curToken().integerLit;
		//-- Must be changed in part 2:
		return (k == plusToken || k==minusToken );
	}*/

}
