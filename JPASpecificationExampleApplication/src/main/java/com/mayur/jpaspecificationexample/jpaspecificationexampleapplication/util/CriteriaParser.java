package com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.util;

import com.google.common.base.Joiner;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriteriaParser {

    private static Map<String, Operator> ops;

    private static Pattern SpecCriteraRegex = Pattern.compile("^(\\w+?)(" + Joiner.on("|")
            .join(SearchOperation.SIMPLE_OPERATION_SET) + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?)$");

    private enum Operator {
        //and is applied first than or
        OR(1), AND(2);
        final int precedence;

        Operator(int p) {
            precedence = p;
        }
    }

    static {
        Map<String, Operator> tempMap = new HashMap<>();
        tempMap.put("AND", Operator.AND);
        tempMap.put("OR", Operator.OR);
        tempMap.put("or", Operator.OR);
        tempMap.put("and", Operator.AND);

        ops = Collections.unmodifiableMap(tempMap);
    }

    private static boolean isHigerPrecedenceOperator(String currOp, String prevOp) {
        return (ops.containsKey(prevOp) && ops.get(prevOp).precedence >= ops.get(currOp).precedence);
    }

    public Deque<?> parse(String searchParam) {

        Deque<Object> output = new LinkedList<>();
        //this stack will only contain operators and nothing else
        Deque<String> stack = new LinkedList<>();

        //We split using space
        Arrays.stream(searchParam.split("\\s+")).forEach(token -> {
            //ops contains operators
            if (ops.containsKey(token)) {
                //we keep higher precedence operators and expression at first so that while poping stack from first it get's removed first
                while (!stack.isEmpty() && isHigerPrecedenceOperator(token, stack.peek()))
                    output.push(stack.pop()
                            .equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR : SearchOperation.AND_OPERATOR);
                stack.push(token.equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR : SearchOperation.AND_OPERATOR);
            } else if (token.equals(SearchOperation.LEFT_PARANTHESIS)) {
                stack.push(SearchOperation.LEFT_PARANTHESIS);
            } else if (token.equals(SearchOperation.RIGHT_PARANTHESIS)) {
                while (!stack.peek()
                        .equals(SearchOperation.LEFT_PARANTHESIS))
                    output.push(stack.pop());
                stack.pop();
            } else {

                Matcher matcher = SpecCriteraRegex.matcher(token);
                //we are pushing data filter's at first so that we can create postfix stack
                while (matcher.find()) {
                    output.push(new SpecSearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5)));
                }
            }
        });

        //Infix expression: f1 AND ( f2 OR f3 )
        //Postfix expression: f1 f2 f3 OR AND
        while (!stack.isEmpty())
            output.push(stack.pop());

        return output;
    }

}
