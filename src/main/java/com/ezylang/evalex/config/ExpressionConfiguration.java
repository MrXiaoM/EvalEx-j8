/*
  Copyright 2012-2022 Udo Klimaschewski

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package com.ezylang.evalex.config;

import static com.ezylang.evalex.config.MapBasedFunctionDictionary.entry;
import static com.ezylang.evalex.config.MapBasedFunctionDictionary.listOf;

import com.ezylang.evalex.data.DataAccessorIfc;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.data.MapBasedDataAccessor;
import com.ezylang.evalex.data.conversion.DefaultEvaluationValueConverter;
import com.ezylang.evalex.data.conversion.EvaluationValueConverterIfc;
import com.ezylang.evalex.functions.FunctionIfc;
import com.ezylang.evalex.functions.basic.AbsFunction;
import com.ezylang.evalex.functions.basic.AverageFunction;
import com.ezylang.evalex.functions.basic.CeilingFunction;
import com.ezylang.evalex.functions.basic.CoalesceFunction;
import com.ezylang.evalex.functions.basic.FactFunction;
import com.ezylang.evalex.functions.basic.FloorFunction;
import com.ezylang.evalex.functions.basic.IfFunction;
import com.ezylang.evalex.functions.basic.Log10Function;
import com.ezylang.evalex.functions.basic.LogFunction;
import com.ezylang.evalex.functions.basic.MaxFunction;
import com.ezylang.evalex.functions.basic.MinFunction;
import com.ezylang.evalex.functions.basic.NotFunction;
import com.ezylang.evalex.functions.basic.RandomFunction;
import com.ezylang.evalex.functions.basic.RoundFunction;
import com.ezylang.evalex.functions.basic.SqrtFunction;
import com.ezylang.evalex.functions.basic.SumFunction;
import com.ezylang.evalex.functions.basic.SwitchFunction;
import com.ezylang.evalex.functions.datetime.DateTimeFormatFunction;
import com.ezylang.evalex.functions.datetime.DateTimeNewFunction;
import com.ezylang.evalex.functions.datetime.DateTimeNowFunction;
import com.ezylang.evalex.functions.datetime.DateTimeParseFunction;
import com.ezylang.evalex.functions.datetime.DateTimeToEpochFunction;
import com.ezylang.evalex.functions.datetime.DateTimeTodayFunction;
import com.ezylang.evalex.functions.datetime.DurationFromMillisFunction;
import com.ezylang.evalex.functions.datetime.DurationNewFunction;
import com.ezylang.evalex.functions.datetime.DurationParseFunction;
import com.ezylang.evalex.functions.datetime.DurationToMillisFunction;
import com.ezylang.evalex.functions.string.StringContains;
import com.ezylang.evalex.functions.string.StringEndsWithFunction;
import com.ezylang.evalex.functions.string.StringFormatFunction;
import com.ezylang.evalex.functions.string.StringLeftFunction;
import com.ezylang.evalex.functions.string.StringLengthFunction;
import com.ezylang.evalex.functions.string.StringLowerFunction;
import com.ezylang.evalex.functions.string.StringMatchesFunction;
import com.ezylang.evalex.functions.string.StringRightFunction;
import com.ezylang.evalex.functions.string.StringSplitFunction;
import com.ezylang.evalex.functions.string.StringStartsWithFunction;
import com.ezylang.evalex.functions.string.StringSubstringFunction;
import com.ezylang.evalex.functions.string.StringTrimFunction;
import com.ezylang.evalex.functions.string.StringUpperFunction;
import com.ezylang.evalex.functions.trigonometric.AcosFunction;
import com.ezylang.evalex.functions.trigonometric.AcosHFunction;
import com.ezylang.evalex.functions.trigonometric.AcosRFunction;
import com.ezylang.evalex.functions.trigonometric.AcotFunction;
import com.ezylang.evalex.functions.trigonometric.AcotHFunction;
import com.ezylang.evalex.functions.trigonometric.AcotRFunction;
import com.ezylang.evalex.functions.trigonometric.AsinFunction;
import com.ezylang.evalex.functions.trigonometric.AsinHFunction;
import com.ezylang.evalex.functions.trigonometric.AsinRFunction;
import com.ezylang.evalex.functions.trigonometric.Atan2Function;
import com.ezylang.evalex.functions.trigonometric.Atan2RFunction;
import com.ezylang.evalex.functions.trigonometric.AtanFunction;
import com.ezylang.evalex.functions.trigonometric.AtanHFunction;
import com.ezylang.evalex.functions.trigonometric.AtanRFunction;
import com.ezylang.evalex.functions.trigonometric.CosFunction;
import com.ezylang.evalex.functions.trigonometric.CosHFunction;
import com.ezylang.evalex.functions.trigonometric.CosRFunction;
import com.ezylang.evalex.functions.trigonometric.CotFunction;
import com.ezylang.evalex.functions.trigonometric.CotHFunction;
import com.ezylang.evalex.functions.trigonometric.CotRFunction;
import com.ezylang.evalex.functions.trigonometric.CscFunction;
import com.ezylang.evalex.functions.trigonometric.CscHFunction;
import com.ezylang.evalex.functions.trigonometric.CscRFunction;
import com.ezylang.evalex.functions.trigonometric.DegFunction;
import com.ezylang.evalex.functions.trigonometric.RadFunction;
import com.ezylang.evalex.functions.trigonometric.SecFunction;
import com.ezylang.evalex.functions.trigonometric.SecHFunction;
import com.ezylang.evalex.functions.trigonometric.SecRFunction;
import com.ezylang.evalex.functions.trigonometric.SinFunction;
import com.ezylang.evalex.functions.trigonometric.SinHFunction;
import com.ezylang.evalex.functions.trigonometric.SinRFunction;
import com.ezylang.evalex.functions.trigonometric.TanFunction;
import com.ezylang.evalex.functions.trigonometric.TanHFunction;
import com.ezylang.evalex.functions.trigonometric.TanRFunction;
import com.ezylang.evalex.operators.OperatorIfc;
import com.ezylang.evalex.operators.arithmetic.InfixDivisionOperator;
import com.ezylang.evalex.operators.arithmetic.InfixMinusOperator;
import com.ezylang.evalex.operators.arithmetic.InfixModuloOperator;
import com.ezylang.evalex.operators.arithmetic.InfixMultiplicationOperator;
import com.ezylang.evalex.operators.arithmetic.InfixPlusOperator;
import com.ezylang.evalex.operators.arithmetic.InfixPowerOfOperator;
import com.ezylang.evalex.operators.arithmetic.PrefixMinusOperator;
import com.ezylang.evalex.operators.arithmetic.PrefixPlusOperator;
import com.ezylang.evalex.operators.booleans.InfixAndOperator;
import com.ezylang.evalex.operators.booleans.InfixEqualsOperator;
import com.ezylang.evalex.operators.booleans.InfixGreaterEqualsOperator;
import com.ezylang.evalex.operators.booleans.InfixGreaterOperator;
import com.ezylang.evalex.operators.booleans.InfixLessEqualsOperator;
import com.ezylang.evalex.operators.booleans.InfixLessOperator;
import com.ezylang.evalex.operators.booleans.InfixNotEqualsOperator;
import com.ezylang.evalex.operators.booleans.InfixOrOperator;
import com.ezylang.evalex.operators.booleans.PrefixNotOperator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;
import lombok.Builder;
import lombok.Getter;

/**
 * The expression configuration can be used to configure various aspects of expression parsing and
 * evaluation. <br>
 * A <code>Builder</code> is provided to create custom configurations, e.g.: <br>
 *
 * <pre>
 *   ExpressionConfiguration config = ExpressionConfiguration.builder().mathContext(MathContext.DECIMAL32).arraysAllowed(false).build();
 * </pre>
 *
 * <br>
 * Additional operators and functions can be added to an existing configuration:<br>
 *
 * <pre>
 *     ExpressionConfiguration.defaultConfiguration()
 *        .withAdditionalOperators(
 *            Map.entry("++", new PrefixPlusPlusOperator()),
 *            Map.entry("++", new PostfixPlusPlusOperator()))
 *        .withAdditionalFunctions(Map.entry("save", new SaveFunction()),
 *            Map.entry("update", new UpdateFunction()));
 * </pre>
 */
@Builder(toBuilder = true)
@Getter
public class ExpressionConfiguration {

  /** The standard set constants for EvalEx. */
  public static final Map<String, EvaluationValue> StandardConstants =
      Collections.unmodifiableMap(getStandardConstants());

  /** Setting the decimal places to unlimited, will disable intermediate rounding. */
  public static final int DECIMAL_PLACES_ROUNDING_UNLIMITED = -1;

  /** The default math context has a precision of 68 and {@link RoundingMode#HALF_EVEN}. */
  public static final MathContext DEFAULT_MATH_CONTEXT =
      new MathContext(68, RoundingMode.HALF_EVEN);

  /**
   * The default date time formatters used when parsing a date string. Each format will be tried and
   * the first matching will be used.
   *
   * <ul>
   *   <li>{@link DateTimeFormatter#ISO_DATE_TIME}
   *   <li>{@link DateTimeFormatter#ISO_DATE}
   *   <li>{@link DateTimeFormatter#ISO_LOCAL_DATE_TIME}
   *   <li>{@link DateTimeFormatter#ISO_LOCAL_DATE}
   * </ul>
   */
  protected static final List<DateTimeFormatter> DEFAULT_DATE_TIME_FORMATTERS =
      new ArrayList<>(
          listOf(
              DateTimeFormatter.ISO_DATE_TIME,
              DateTimeFormatter.ISO_DATE,
              DateTimeFormatter.ISO_LOCAL_DATE_TIME,
              DateTimeFormatter.ISO_LOCAL_DATE,
              DateTimeFormatter.RFC_1123_DATE_TIME));

  /** The operator dictionary holds all operators that will be allowed in an expression. */
  @Builder.Default
  @SuppressWarnings("unchecked")
  private final OperatorDictionaryIfc operatorDictionary =
      MapBasedOperatorDictionary.ofOperators(
          // arithmetic
          entry("+", new PrefixPlusOperator()),
          entry("-", new PrefixMinusOperator()),
          entry("+", new InfixPlusOperator()),
          entry("-", new InfixMinusOperator()),
          entry("*", new InfixMultiplicationOperator()),
          entry("/", new InfixDivisionOperator()),
          entry("^", new InfixPowerOfOperator()),
          entry("%", new InfixModuloOperator()),
          // booleans
          entry("=", new InfixEqualsOperator()),
          entry("==", new InfixEqualsOperator()),
          entry("!=", new InfixNotEqualsOperator()),
          entry("<>", new InfixNotEqualsOperator()),
          entry(">", new InfixGreaterOperator()),
          entry(">=", new InfixGreaterEqualsOperator()),
          entry("<", new InfixLessOperator()),
          entry("<=", new InfixLessEqualsOperator()),
          entry("&&", new InfixAndOperator()),
          entry("||", new InfixOrOperator()),
          entry("!", new PrefixNotOperator()));

  /** The function dictionary holds all functions that will be allowed in an expression. */
  @Builder.Default
  @SuppressWarnings("unchecked")
  private final FunctionDictionaryIfc functionDictionary =
      MapBasedFunctionDictionary.ofFunctions(
          // basic functions
          entry("ABS", new AbsFunction()),
          entry("AVERAGE", new AverageFunction()),
          entry("CEILING", new CeilingFunction()),
          entry("COALESCE", new CoalesceFunction()),
          entry("FACT", new FactFunction()),
          entry("FLOOR", new FloorFunction()),
          entry("IF", new IfFunction()),
          entry("LOG", new LogFunction()),
          entry("LOG10", new Log10Function()),
          entry("MAX", new MaxFunction()),
          entry("MIN", new MinFunction()),
          entry("NOT", new NotFunction()),
          entry("RANDOM", new RandomFunction()),
          entry("ROUND", new RoundFunction()),
          entry("SQRT", new SqrtFunction()),
          entry("SUM", new SumFunction()),
          entry("SWITCH", new SwitchFunction()),
          // trigonometric
          entry("ACOS", new AcosFunction()),
          entry("ACOSH", new AcosHFunction()),
          entry("ACOSR", new AcosRFunction()),
          entry("ACOT", new AcotFunction()),
          entry("ACOTH", new AcotHFunction()),
          entry("ACOTR", new AcotRFunction()),
          entry("ASIN", new AsinFunction()),
          entry("ASINH", new AsinHFunction()),
          entry("ASINR", new AsinRFunction()),
          entry("ATAN", new AtanFunction()),
          entry("ATAN2", new Atan2Function()),
          entry("ATAN2R", new Atan2RFunction()),
          entry("ATANH", new AtanHFunction()),
          entry("ATANR", new AtanRFunction()),
          entry("COS", new CosFunction()),
          entry("COSH", new CosHFunction()),
          entry("COSR", new CosRFunction()),
          entry("COT", new CotFunction()),
          entry("COTH", new CotHFunction()),
          entry("COTR", new CotRFunction()),
          entry("CSC", new CscFunction()),
          entry("CSCH", new CscHFunction()),
          entry("CSCR", new CscRFunction()),
          entry("DEG", new DegFunction()),
          entry("RAD", new RadFunction()),
          entry("SIN", new SinFunction()),
          entry("SINH", new SinHFunction()),
          entry("SINR", new SinRFunction()),
          entry("SEC", new SecFunction()),
          entry("SECH", new SecHFunction()),
          entry("SECR", new SecRFunction()),
          entry("TAN", new TanFunction()),
          entry("TANH", new TanHFunction()),
          entry("TANR", new TanRFunction()),
          // string functions
          entry("STR_CONTAINS", new StringContains()),
          entry("STR_ENDS_WITH", new StringEndsWithFunction()),
          entry("STR_FORMAT", new StringFormatFunction()),
          entry("STR_LEFT", new StringLeftFunction()),
          entry("STR_LENGTH", new StringLengthFunction()),
          entry("STR_LOWER", new StringLowerFunction()),
          entry("STR_MATCHES", new StringMatchesFunction()),
          entry("STR_RIGHT", new StringRightFunction()),
          entry("STR_SPLIT", new StringSplitFunction()),
          entry("STR_STARTS_WITH", new StringStartsWithFunction()),
          entry("STR_SUBSTRING", new StringSubstringFunction()),
          entry("STR_TRIM", new StringTrimFunction()),
          entry("STR_UPPER", new StringUpperFunction()),
          // date time functions
          entry("DT_DATE_NEW", new DateTimeNewFunction()),
          entry("DT_DATE_PARSE", new DateTimeParseFunction()),
          entry("DT_DATE_FORMAT", new DateTimeFormatFunction()),
          entry("DT_DATE_TO_EPOCH", new DateTimeToEpochFunction()),
          entry("DT_DURATION_NEW", new DurationNewFunction()),
          entry("DT_DURATION_FROM_MILLIS", new DurationFromMillisFunction()),
          entry("DT_DURATION_TO_MILLIS", new DurationToMillisFunction()),
          entry("DT_DURATION_PARSE", new DurationParseFunction()),
          entry("DT_NOW", new DateTimeNowFunction()),
          entry("DT_TODAY", new DateTimeTodayFunction()));

  /** The math context to use. */
  @Builder.Default private final MathContext mathContext = DEFAULT_MATH_CONTEXT;

  /**
   * The data accessor is responsible for accessing variable and constant values in an expression.
   * The supplier will be called once for each new expression, the default is to create a new {@link
   * MapBasedDataAccessor} instance for each expression, providing a new storage for each
   * expression.
   */
  @Builder.Default
  private final Supplier<DataAccessorIfc> dataAccessorSupplier = MapBasedDataAccessor::new;

  /**
   * Default constants will be added automatically to each expression and can be used in expression
   * evaluation.
   */
  @Builder.Default
  private final Map<String, EvaluationValue> defaultConstants = getStandardConstants();

  /** Support for arrays in expressions are allowed or not. */
  @Builder.Default private final boolean arraysAllowed = true;

  /** Support for structures in expressions are allowed or not. */
  @Builder.Default private final boolean structuresAllowed = true;

  /**
   * Support for the binary (undefined) data type is allowed or not.
   *
   * @since 3.3.0
   */
  @Builder.Default private final boolean binaryAllowed = false;

  /** Support for implicit multiplication, like in (a+b)(b+c) are allowed or not. */
  @Builder.Default private final boolean implicitMultiplicationAllowed = true;

  /** Support for single quote string literals, like in 'Hello World' are allowed or not. */
  @Builder.Default private final boolean singleQuoteStringLiteralsAllowed = false;

  /**
   * The power of operator precedence, can be set higher {@link
   * OperatorIfc#OPERATOR_PRECEDENCE_POWER_HIGHER} or to a custom value.
   */
  @Builder.Default private final int powerOfPrecedence = OperatorIfc.OPERATOR_PRECEDENCE_POWER;

  /**
   * If specified, only the final result of the evaluation will be rounded to the specified number
   * of decimal digits, using the MathContexts rounding mode.
   *
   * <p>The default value of _DECIMAL_PLACES_ROUNDING_UNLIMITED_ will disable rounding.
   */
  @Builder.Default private final int decimalPlacesResult = DECIMAL_PLACES_ROUNDING_UNLIMITED;

  /**
   * If specified, all results from operations and functions will be rounded to the specified number
   * of decimal digits, using the MathContexts rounding mode.
   *
   * <p>Automatic rounding is disabled by default. When enabled, EvalEx will round all input
   * variables, constants, intermediate operation and function results and the final result to the
   * specified number of decimal digits, using the current rounding mode. Using a value of
   * _DECIMAL_PLACES_ROUNDING_UNLIMITED_ will disable automatic rounding.
   */
  @Builder.Default private final int decimalPlacesRounding = DECIMAL_PLACES_ROUNDING_UNLIMITED;

  /**
   * If set to true (default), then the trailing decimal zeros in a number result will be stripped.
   */
  @Builder.Default private final boolean stripTrailingZeros = true;

  /**
   * If set to true (default), then variables can be set that have the name of a constant. In that
   * case, the constant value will be removed and a variable value will be set.
   */
  @Builder.Default private final boolean allowOverwriteConstants = true;

  /** The time zone id. By default, the system default zone ID is used. */
  @Builder.Default private final ZoneId zoneId = ZoneId.systemDefault();

  /** The locale. By default, the system default locale is used. */
  @Builder.Default private final Locale locale = Locale.getDefault();

  /**
   * The date-time formatters. When parsing, each format will be tried and the first matching will
   * be used. For formatting, only the first will be used.
   *
   * <p>By default, the {@link ExpressionConfiguration#DEFAULT_DATE_TIME_FORMATTERS} are used.
   */
  @Builder.Default
  private final List<DateTimeFormatter> dateTimeFormatters = DEFAULT_DATE_TIME_FORMATTERS;

  /** The converter to use when converting different data types to an {@link EvaluationValue}. */
  @Builder.Default
  private final EvaluationValueConverterIfc evaluationValueConverter =
      new DefaultEvaluationValueConverter();

  /**
   * Convenience method to create a default configuration.
   *
   * @return A configuration with default settings.
   */
  public static ExpressionConfiguration defaultConfiguration() {
    return ExpressionConfiguration.builder().build();
  }

  private static Map<String, EvaluationValue> getStandardConstants() {

    Map<String, EvaluationValue> constants = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    constants.put("TRUE", EvaluationValue.TRUE);
    constants.put("FALSE", EvaluationValue.FALSE);
    constants.put(
        "PI",
        EvaluationValue.numberValue(
            new BigDecimal(
                "3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679")));
    constants.put(
        "E",
        EvaluationValue.numberValue(
            new BigDecimal(
                "2.71828182845904523536028747135266249775724709369995957496696762772407663")));
    constants.put("NULL", EvaluationValue.NULL_VALUE);

    constants.put(
        "DT_FORMAT_ISO_DATE_TIME",
        EvaluationValue.stringValue("yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]['['VV']']"));
    constants.put(
        "DT_FORMAT_LOCAL_DATE_TIME", EvaluationValue.stringValue("yyyy-MM-dd'T'HH:mm:ss[.SSS]"));
    constants.put("DT_FORMAT_LOCAL_DATE", EvaluationValue.stringValue("yyyy-MM-dd"));

    return constants;
  }

  /**
   * Adds additional operators to this configuration.
   *
   * @param operators variable number of arguments with a map entry holding the operator name and
   *     implementation. <br>
   *     Example: <code>
   *                  ExpressionConfiguration.defaultConfiguration() .withAdditionalOperators(
   *                  Map.entry("++", new PrefixPlusPlusOperator()), Map.entry("++", new
   *                  PostfixPlusPlusOperator()));
   *                  </code>
   * @return The modified configuration, to allow chaining of methods.
   */
  @SafeVarargs
  public final ExpressionConfiguration withAdditionalOperators(
      Map.Entry<String, OperatorIfc>... operators) {
    Arrays.stream(operators)
        .forEach(entry -> operatorDictionary.addOperator(entry.getKey(), entry.getValue()));
    return this;
  }

  /**
   * Adds additional functions to this configuration.
   *
   * @param functions variable number of arguments with a map entry holding the functions name and
   *     implementation. <br>
   *     Example: <code>
   *                  ExpressionConfiguration.defaultConfiguration() .withAdditionalFunctions(
   *                  Map.entry("save", new SaveFunction()), Map.entry("update", new
   *                  UpdateFunction()));
   *                  </code>
   * @return The modified configuration, to allow chaining of methods.
   */
  @SafeVarargs
  public final ExpressionConfiguration withAdditionalFunctions(
      Map.Entry<String, FunctionIfc>... functions) {
    Arrays.stream(functions)
        .forEach(entry -> functionDictionary.addFunction(entry.getKey(), entry.getValue()));
    return this;
  }
}
