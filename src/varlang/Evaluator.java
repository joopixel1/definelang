package varlang;
import static varlang.AST.*;
import static varlang.Value.*;

import java.util.List;

import varlang.AST.AddExp;
import varlang.AST.Const;
import varlang.AST.DivExp;
import varlang.AST.ErrorExp;
import varlang.AST.MultExp;
import varlang.AST.Program;
import varlang.AST.SubExp;
import varlang.AST.VarExp;
import varlang.AST.Visitor;

public class Evaluator implements Visitor<Value> {
	
	Value valueOf(Program p) {
		// Value of a program in this language is the value of the expression
		return (Value) p.accept(this);
	}
	
	@Override
	public Value visit(AddExp e) {
		List<Exp> operands = e.all();
		int result = 0;
		for(Exp exp: operands) {
			Int intermediate = (Int) exp.accept(this); // Dynamic type-checking
			result += intermediate.v(); //Semantics of AddExp in terms of the target language.
		}
		return new Int(result);
	}

	@Override
	public Value visit(Const e) {
		return new Int(e.v());
	}

	@Override
	public Value visit(DivExp e) {
		List<Exp> operands = e.all();
		Int lVal = (Int) operands.get(0).accept(this);
		Int rVal = (Int) operands.get(1).accept(this);
		return new Int(lVal.v() / rVal.v());
	}

	@Override
	public Value visit(ErrorExp e) {
		return new Value.DynamicError("Encountered an error expression");
	}

	@Override
	public Value visit(MultExp e) {
		List<Exp> operands = e.all();
		Int lVal = (Int) operands.get(0).accept(this);
		Int rVal = (Int) operands.get(1).accept(this);
		return new Int(lVal.v() * rVal.v());
	}

	@Override
	public Value visit(Program p) {
		return (Value) p.e().accept(this);
	}

	@Override
	public Value visit(SubExp e) {
		List<Exp> operands = e.all();
		Int lVal = (Int) operands.get(0).accept(this);
		Int rVal = (Int) operands.get(1).accept(this);
		return new Int(lVal.v() - rVal.v());
	}

	@Override
	public Value visit(VarExp e) {
		// Previously, all variables had value 42. New semantics.
		return new Int(42); // TODO:
	}	

	@Override
	public Value visit(LetExp e) { // New for varlang.
		return new Int(42); // TODO:
	}	
	
}
