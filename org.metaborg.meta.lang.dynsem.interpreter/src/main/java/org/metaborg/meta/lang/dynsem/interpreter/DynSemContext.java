package org.metaborg.meta.lang.dynsem.interpreter;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.metaborg.meta.lang.dynsem.interpreter.nodes.rules.RuleRegistry;

public class DynSemContext {
	public static DynSemLanguage LANGUAGE;

	private final InputStream input;
	private final PrintStream output;

	private final IDynSemLanguageParser parser;
	private final ITermRegistry termRegistry;
	private final RuleRegistry ruleRegistry;

	private final Map<String, Object> properties;

	public DynSemContext(IDynSemLanguageParser parser, ITermRegistry termRegistry, RuleRegistry ruleRegistry) {
		this(parser, termRegistry, ruleRegistry, System.in, System.out);
	}

	public DynSemContext(IDynSemLanguageParser parser, ITermRegistry termRegistry, RuleRegistry ruleRegistry,
			InputStream input, PrintStream output) {
		this.parser = parser;
		this.termRegistry = termRegistry;
		this.ruleRegistry = ruleRegistry;
		this.input = input;
		this.output = output;
		this.properties = new HashMap<>();
	}

	public IDynSemLanguageParser getParser() {
		return parser;
	}

	public RuleRegistry getRuleRegistry() {
		return ruleRegistry;
	}

	public ITermRegistry getTermRegistry() {
		return termRegistry;
	}

	public Object readProperty(String prop, Object defaultValue) {
		final Object val = properties.get(prop);
		if (val != null) {
			return val;
		}
		return defaultValue;
	}

	public DynSemContext writeProperty(String prop, Object val) {
		properties.put(prop, val);
		return this;
	}

	// public ITermBuildFactory lookupTermBuilder(String name, int arity) {
	// ITermBuildFactory f = termRegistry.lookupBuildFactory(name, arity);
	// assert f != null;
	// return f;
	// }
	//
	// public ITermBuildFactory lookupNativeOpBuilder(String name, int arity) {
	// ITermBuildFactory f = termRegistry.lookupNativeOpBuildFactory(name, arity);
	// assert f != null;
	// return f;
	// }
	//
	// public ITermMatchPatternFactory lookupMatchPattern(String name, int arity) {
	// ITermMatchPatternFactory f = termRegistry.lookupMatchFactory(name, arity);
	// assert f != null;
	// return f;
	// }
	//
	// public ITermBuildFactory lookupNativeTypeAdapterBuildFactory(String sort, String function, int arity) {
	// ITermBuildFactory f = termRegistry.lookupNativeTypeAdapterBuildFactory(sort, function, arity);
	// assert f != null;
	// return f;
	// }

	public InputStream getInput() {
		return input;
	}

	public PrintStream getOutput() {
		return output;
	}

}
