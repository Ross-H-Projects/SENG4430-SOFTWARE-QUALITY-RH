package group3.metric_analysis.conditonal_nesting.metric_trackers;

import group3.metric_analysis.conditonal_nesting.source_code_elements.ForLoop;
import group3.metric_analysis.conditonal_nesting.source_code_elements.IfStatement;
import group3.metric_analysis.conditonal_nesting.source_code_elements.SwitchStatement;
import group3.metric_analysis.conditonal_nesting.source_code_elements.WhileLoop;

import java.util.ArrayList;

public class MetricTracker {
    private ArrayList<IfStatement> ifStatementArrayList;
    private ArrayList<SwitchStatement> switchStatementArrayList;
    private ArrayList<WhileLoop> whileLoopArrayList;
    private ArrayList<ForLoop> forLoopArrayList;

    public MetricTracker() {
    }

    public void addNewIfStatement(IfStatement ifStatement) {
        ifStatementArrayList.add(ifStatement);
    }
    public void addNewSwitchStatement(SwitchStatement switchStatement) {
        switchStatementArrayList.add(switchStatement);
    }
    public void addNewWhileLoop(WhileLoop whileLoop) {
        whileLoopArrayList.add(whileLoop);
    }
    public void addNewForLoop(ForLoop forLoop) {
        forLoopArrayList.add(forLoop);
    }

    public ArrayList<IfStatement> getIfStatementArrayList() {
        return ifStatementArrayList;
    }
    public ArrayList<SwitchStatement> getSwitchStatementArrayList() {
        return switchStatementArrayList;
    }
    public ArrayList<WhileLoop> getWhileLoopArrayList() {
        return whileLoopArrayList;
    }
    public ArrayList<ForLoop> getForLoopArrayList() {
        return forLoopArrayList;
    }
}



