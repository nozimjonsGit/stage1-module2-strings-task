package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {

        String accessModifier = null;
        String returnType = null;
        String methodName = null;

        // Create argument list
        List<MethodSignature.Argument> listOfArguments = new ArrayList<>();


        // Divide signatureString into tokens by space
        StringTokenizer tokenizer =  new StringTokenizer(signatureString, " ");
        List<String> tokens = new ArrayList<>();

        // Check if tokenizer has token
        if (tokenizer.hasMoreTokens()) {

            String token = tokenizer.nextToken();

            // Check if a first token is a valid access modifier
            if (isValidAccessModifier(token))
                accessModifier = token;
            else
                tokenizer = new StringTokenizer(signatureString, " ");

        }

        // Check next token for return type
        if (tokenizer.hasMoreTokens()) {
            returnType = tokenizer.nextToken();
        }


        if (tokenizer.hasMoreTokens()) {
            String methodAndArgs = tokenizer.nextToken();
            int method = methodAndArgs.indexOf("("); // get the index of the (, as method name ends with open parenthesis
            methodName = methodAndArgs.substring(0, method); // assign method name

            String firstArg = methodAndArgs.substring(method + 1); // get first argument after open parenthesis

            // Check if the first argument is close parenthesis - if true then it is not an argument
            if (!firstArg.equals(")"))
                tokens.add(firstArg);

            // Search for commas and replace them
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().replace(",", "");
                if (token.contains(")")){
                    token = token.substring(0, token.length() - 1);
                }
                tokens.add(token);
            }
        }


        if (tokens.size() != 0) {
            // Assign values to argument type and argument name
            for (int i = 0; i < tokens.size(); i += 2) {
                String argType = tokens.get(i);
                String argName = tokens.get(i + 1);

                listOfArguments.add(new MethodSignature.Argument(argType,argName));
            }
        }

        // Create MethodSignature object and assign values
        MethodSignature methodSignature = new MethodSignature(methodName, listOfArguments);
        methodSignature.setReturnType(returnType);
        methodSignature.setAccessModifier(accessModifier);
        return methodSignature;
    }

    public boolean  isValidAccessModifier(String accessModifier) {
        return accessModifier.equals("public") || accessModifier.equals("private")
                || accessModifier.equals("protected");
    }
}
