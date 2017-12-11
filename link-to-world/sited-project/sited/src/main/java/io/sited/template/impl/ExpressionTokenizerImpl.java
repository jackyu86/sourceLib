package io.sited.template.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import io.sited.StandardException;
import io.sited.template.impl.token.AddToken;
import io.sited.template.impl.token.CompareToken;
import io.sited.template.impl.token.FieldToken;
import io.sited.template.impl.token.FilterToken;
import io.sited.template.impl.token.MethodToken;
import io.sited.template.impl.token.NotToken;
import io.sited.template.impl.token.StringToken;
import io.sited.template.impl.token.TernaryToken;
import io.sited.template.impl.token.TokenWrapper;
import io.sited.template.impl.token.Tokens;

import java.util.Collections;
import java.util.List;

import static io.sited.template.impl.token.Tokens.isBool;
import static io.sited.template.impl.token.Tokens.isIdentifier;

/**
 * @author chi
 */
public class ExpressionTokenizerImpl implements ExpressionTokenizer {
    @Override
    public Token tokenize(String expression) {
        Token root = new TokenWrapper();
        doTokenize(expression, 0, expression.length(), root, true);
        return root.children.get(0);
    }

    private void doTokenize(String expression, int start, int end, Token last, boolean firstToken) {
        StringBuilder b = new StringBuilder();
        for (int i = start; i < end; i++) {
            char c = expression.charAt(i);

            if (i == end - 1) {
                b.append(c);
                String field = b.toString();
                Token token = firstToken ? firstToken(field, last) : nextToken(field, last);
                last.addChild(token);
                return;
            } else if (c == '.') {
                String field = b.toString();
                if (!isIdentifier(field)) {
                    throw new StandardException("invalid field, field={}", field);
                }
                Token token = firstToken ? firstToken(field, last) : nextToken(field, last);
                last.addChild(token);
                doTokenize(expression, i + 1, end, token, false);
                return;
            } else if (c == '\'') {
                Token token = null;
                int j = i + 1;
                for (; j < end; j++) {
                    char c1 = expression.charAt(j);
                    if (c1 == '\'' && expression.charAt(j - 1) != '\\') {
                        token = new StringToken(expression.substring(i + 1, j), last);
                        break;
                    }
                }

                last.addChild(token);

                if (j + 1 < end && expression.charAt(j + 1) == '.') {
                    doTokenize(expression, j + 2, end, token, false);
                    return;
                }

                doTokenize(expression, j + 1, end, token, true);
                return;
            } else if (c == '\"') {
                Token token = null;

                int j = i + 1;
                for (; j < end; j++) {
                    char c1 = expression.charAt(j);
                    if (c1 == '\"' && expression.charAt(j - 1) != '\\') {
                        token = new StringToken(expression.substring(i + 1, j), last);
                        break;
                    }
                }

                last.addChild(token);

                if (j + 1 < end && expression.charAt(j + 1) == '.') {
                    doTokenize(expression, j + 2, end, token, false);
                    return;
                }

                doTokenize(expression, j + 1, end, token, true);
                return;
            } else if (c == '?') {
                Token leaf = last;
                if (b.length() > 0) {
                    String field = b.toString();
                    Token token = firstToken ? firstToken(field, last) : nextToken(field, last);
                    last.addChild(token);
                    leaf = token;
                }

                Token conditionToken = ternaryCondition(leaf);
                Token ternaryToken = new TernaryToken(conditionToken.parent);
                conditionToken.parent.replaceChild(conditionToken, ternaryToken);
                ternaryToken.addChild(conditionToken);
                doTokenize(expression, i + 1, end, ternaryToken, true);
                return;
            } else if (c == ':') {
                Token leaf = last;
                if (b.length() > 0) {
                    String field = b.toString();
                    Token token = firstToken ? firstToken(field, last) : nextToken(field, last);
                    last.addChild(token);
                    leaf = token;
                }

                Token ternaryToken = ternary(leaf);
                doTokenize(expression, i + 1, end, ternaryToken, true);
                return;
            } else if (c == '<') {
                Token leaf = last;
                if (b.length() > 0) {
                    String field = b.toString();
                    Token token = firstToken ? firstToken(field, last) : nextToken(field, last);
                    last.addChild(token);
                    leaf = token;
                }

                String operator = "<";
                if (i + 1 < end && expression.charAt(i) == '=') {
                    operator += '=';
                    i++;
                }

                Token token = compareLeft(leaf);
                Token compareToken = new CompareToken(operator, token.parent);
                token.parent.replaceChild(token, compareToken);
                compareToken.addChild(token);

                doTokenize(expression, i + 1, end, compareToken, true);
                return;
            } else if (c == '>') {
                Token leaf = last;
                if (b.length() > 0) {
                    String field = b.toString();
                    Token token = firstToken ? firstToken(field, last) : nextToken(field, last);
                    last.addChild(token);
                    leaf = token;
                }

                String operator = ">";
                if (i + 1 < end && expression.charAt(i + 1) == '=') {
                    operator += '=';
                    i++;
                }

                Token token = compareLeft(leaf);
                Token compareToken = new CompareToken(operator, token.parent);
                token.parent.replaceChild(token, compareToken);
                compareToken.addChild(token);

                doTokenize(expression, i + 1, end, compareToken, true);
                return;
            } else if (c == '=') {
                Token leaf = last;
                if (b.length() > 0) {
                    String field = b.toString();
                    Token token = firstToken ? firstToken(field, last) : nextToken(field, last);
                    last.addChild(token);
                    leaf = token;
                }
                if (i + 1 >= end || expression.charAt(i + 1) != '=') {
                    throw new StandardException("invalid equal operator, content={}", expression);
                }
                String operator = "==";
                i++;

                Token token = compareLeft(leaf);
                Token equalToken = new CompareToken(operator, token.parent);
                token.parent.replaceChild(token, equalToken);
                equalToken.addChild(token);
                doTokenize(expression, i + 1, end, equalToken, true);
                return;
            } else if (c == '!') {
                Token leaf = last;
                if (b.length() > 0) {
                    String field = b.toString();
                    Token token = firstToken ? firstToken(field, last) : nextToken(field, last);
                    last.addChild(token);
                    leaf = token;
                }
                if (i + 1 >= end || expression.charAt(i + 1) == '=') {
                    //
                    String operator = "!=";
                    i++;

                    Token token = compareLeft(leaf);
                    Token equalToken = new CompareToken(operator, token.parent);
                    token.parent.replaceChild(token, equalToken);
                    equalToken.addChild(token);
                    doTokenize(expression, i + 1, end, equalToken, true);
                    return;
                } else {
                    Token token = new NotToken(last);
                    leaf.addChild(token);
                    doTokenize(expression, i + 1, end, token, true);
                    return;
                }
            } else if (c == '(') {
                String methodName = b.toString();
                int methodEnd = expression.indexOf(')', i + 1);

                if (methodEnd < 0) {
                    throw new StandardException("invalid method, method={}", expression);
                }

                List<Token> params = Lists.newArrayList();
                Splitter.on(',').omitEmptyStrings().split(expression.subSequence(i + 1, methodEnd)).forEach(param -> {
                    TokenWrapper tokenWrapper = new TokenWrapper();
                    doTokenize(param, 0, param.length(), tokenWrapper, true);
                    params.add(tokenWrapper.token());
                });

                MethodToken methodToken = new MethodToken(methodName, last, params);
                last.addChild(methodToken);

                if (methodEnd + 1 < end && expression.charAt(methodEnd + 1) == '.') {
                    doTokenize(expression, methodEnd + 2, end, methodToken, false);
                    return;
                } else {
                    doTokenize(expression, methodEnd + 1, end, methodToken, true);
                    return;
                }
            } else if (c == '|') {
                Token current = last;
                if (b.length() > 0) {
                    String field = b.toString();
                    Token token = firstToken ? firstToken(field, last) : nextToken(field, last);
                    last.addChild(token);
                    current = token;
                }

                List<String> filters = Splitter.on('|').omitEmptyStrings().splitToList(expression.substring(i + 1, end));

                for (String filter : filters) {
                    Token filterToken = filter(filter);
                    current.addChild(filterToken);
                    current = filterToken;
                }
                return;
            } else if (c == '+') {
                Token leaf = last;
                if (b.length() > 0) {
                    String field = b.toString();
                    Token token = firstToken ? firstToken(field, last) : nextToken(field, last);
                    last.addChild(token);
                    leaf = token;
                }

                Token token = compareLeft(leaf);
                Token exprToken = new AddToken(token.parent);
                token.parent.replaceChild(token, exprToken);
                exprToken.addChild(token);

                doTokenize(expression, i + 1, end, exprToken, true);
                return;
            } else if (!Character.isWhitespace(c)) {
                b.append(c);
            }
        }
    }

    private Token firstToken(String content, Token parent) {
        if (Tokens.isInteger(content)) {
            return new Token("Integer", content, parent);
        } else if (Tokens.isDouble(content)) {
            return new Token("Double", content, parent);
        } else if (Tokens.isNull(content)) {
            return new Token("Null", content, parent);
        } else if (isBool(content)) {
            return new Token("Boolean", content, parent);
        } else if (isIdentifier(content)) {
            return new FieldToken(content, parent);
        } else {
            throw new StandardException("invalid token, token={}", content);
        }
    }

    private Token nextToken(String content, Token parent) {
        if (!isIdentifier(content)) {
            throw new StandardException("invalid token, token={}", content);
        }
        return new FieldToken(content, parent);
    }

    private Token ternaryCondition(Token token) {
        Token current = token;
        while (current != null) {
            Token parent = current.parent;
            if (TernaryToken.isTernaryToken(parent) || TokenWrapper.isTokenWrapper(parent)) {
                return current;
            }
            current = current.parent;
        }
        return null;
    }

    private Token ternary(Token token) {
        Token current = token;
        while (current != null) {
            if (TernaryToken.isTernaryToken(current)) {
                return current;
            }
            current = current.parent;
        }
        throw new StandardException("invalid ternary token, token={}", token.content);
    }

    private Token compareLeft(Token token) {
        Token current = token;
        while (current != null) {
            Token parent = current.parent;
            if (TernaryToken.isTernaryToken(parent) || TokenWrapper.isTokenWrapper(parent) || CompareToken.isCompareToken(parent)) {
                return current;
            }
            current = current.parent;
        }
        throw new StandardException("invalid compare token, token={}", token.content);
    }

    private Token filter(String content) {
        int start = content.indexOf('(');
        if (start > 0) {
            if (content.charAt(content.length() - 1) != ')') {
                throw new StandardException("invalid filter, filter={}", content);
            }
            List<Token> params = Lists.newArrayList();
            Splitter.on(',').omitEmptyStrings().split(content.subSequence(start + 1, content.length() - 1)).forEach(param -> {
                TokenWrapper wrapper = new TokenWrapper();
                doTokenize(param, 0, param.length(), wrapper, true);
                params.add(wrapper.children.get(0));
            });
            return new FilterToken(content.substring(0, start).trim(), null, params);
        } else {
            return new FilterToken(content.trim(), null, Collections.emptyList());
        }
    }
}
