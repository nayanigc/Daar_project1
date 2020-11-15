import java.util.ArrayList;

public class RegExTreeProf {
    //MACROS
    static final int CONCAT = 0xC04CA7;
    static final int ETOILE = 0xE7011E;
    static final int ALTERN = 0xA17E54;
    static final int PROTECTION = 0xBADDAD;

    static final int PARENTHESEOUVRANT = 0x16641664;
    static final int PARENTHESEFERMANT = 0x51515151;
    static final int DOT = 0xD07;

    protected int root;
    protected ArrayList<RegExTreeProf> subTrees;
    public RegExTreeProf(int root, ArrayList<RegExTreeProf> subTrees) {
        this.root = root;
        this.subTrees = subTrees;
    }

    public static RegExTreeProf parse(String regEx) throws Exception {
        //BEGIN DEBUG: set conditionnal to true for debug example
        if (false) throw new Exception();
        RegExTreeProf example = exampleAhoUllman();
        if (false) return example;
        //END DEBUG
        ArrayList<RegExTreeProf> result = new ArrayList<RegExTreeProf>();
        for (int i=0;i<regEx.length();i++) result.add(new RegExTreeProf(charToRoot(regEx.charAt(i)),new ArrayList<RegExTreeProf>()));
        return parse(result);
    } 
    private static int charToRoot(char c) {
        if (c=='.') return DOT;
        if (c=='*') return ETOILE;
        if (c=='|') return ALTERN;
        if (c=='(') return PARENTHESEOUVRANT;
        if (c==')') return PARENTHESEFERMANT;
        return (int)c;
    }
    private static RegExTreeProf parse(ArrayList<RegExTreeProf> result) throws Exception {
        while (containParenthese(result)) result=processParenthese(result);
        while (containEtoile(result)) result=processEtoile(result);
        while (containConcat(result)) result=processConcat(result);
        while (containAltern(result)) result=processAltern(result);

        if (result.size()>1) throw new Exception();

        return removeProtection(result.get(0));
    }
    private static boolean containParenthese(ArrayList<RegExTreeProf> trees) {
        for (RegExTreeProf t: trees) if (t.root==PARENTHESEFERMANT || t.root==PARENTHESEOUVRANT) return true;
        return false;
    }
    private static ArrayList<RegExTreeProf> processParenthese(ArrayList<RegExTreeProf> trees) throws Exception {
        ArrayList<RegExTreeProf> result = new ArrayList<RegExTreeProf>();
        boolean found = false;
        for (RegExTreeProf t: trees) {
            if (!found && t.root==PARENTHESEFERMANT) {
            boolean done = false;
            ArrayList<RegExTreeProf> content = new ArrayList<RegExTreeProf>();
            while (!done && !result.isEmpty())
                if (result.get(result.size()-1).root==PARENTHESEOUVRANT) { done = true; result.remove(result.size()-1); }
                else content.add(0,result.remove(result.size()-1));
            if (!done) throw new Exception();
            found = true;
            ArrayList<RegExTreeProf> subTrees = new ArrayList<RegExTreeProf>();
            subTrees.add(parse(content));
            result.add(new RegExTreeProf(PROTECTION, subTrees));
            } else {
            result.add(t);
            }
        }
        if (!found) throw new Exception();
        return result;
    }
    private static boolean containEtoile(ArrayList<RegExTreeProf> trees) {
        for (RegExTreeProf t: trees) if (t.root==ETOILE && t.subTrees.isEmpty()) return true;
        return false;
    }
    private static ArrayList<RegExTreeProf> processEtoile(ArrayList<RegExTreeProf> trees) throws Exception {
        ArrayList<RegExTreeProf> result = new ArrayList<RegExTreeProf>();
        boolean found = false;
        for (RegExTreeProf t: trees) {
            if (!found && t.root==ETOILE && t.subTrees.isEmpty()) {
            if (result.isEmpty()) throw new Exception();
            found = true;
            RegExTreeProf last = result.remove(result.size()-1);
            ArrayList<RegExTreeProf> subTrees = new ArrayList<RegExTreeProf>();
            subTrees.add(last);
            result.add(new RegExTreeProf(ETOILE, subTrees));
            } else {
                result.add(t);
            }
        }
        return result;
    }
    private static boolean containConcat(ArrayList<RegExTreeProf> trees) {
        boolean firstFound = false;
        for (RegExTreeProf t: trees) {
            if (!firstFound && t.root!=ALTERN) { firstFound = true; continue; }
            if (firstFound) if (t.root!=ALTERN) return true; else firstFound = false;
        }
        return false;
    }
    private static ArrayList<RegExTreeProf> processConcat(ArrayList<RegExTreeProf> trees) throws Exception {
        ArrayList<RegExTreeProf> result = new ArrayList<RegExTreeProf>();
        boolean found = false;
        boolean firstFound = false;
        for (RegExTreeProf t: trees) {
            if (!found && !firstFound && t.root!=ALTERN) {
            firstFound = true;
            result.add(t);
            continue;
            }
            if (!found && firstFound && t.root==ALTERN) {
            firstFound = false;
            result.add(t);
            continue;
            }
            if (!found && firstFound && t.root!=ALTERN) {
            found = true;
            RegExTreeProf last = result.remove(result.size()-1);
            ArrayList<RegExTreeProf> subTrees = new ArrayList<RegExTreeProf>();
            subTrees.add(last);
            subTrees.add(t);
            result.add(new RegExTreeProf(CONCAT, subTrees));
            } else {
            result.add(t);
            }
        }
        return result;
    }
    private static boolean containAltern(ArrayList<RegExTreeProf> trees) {
        for (RegExTreeProf t: trees) if (t.root==ALTERN && t.subTrees.isEmpty()) return true;
        return false;
    }
    private static ArrayList<RegExTreeProf> processAltern(ArrayList<RegExTreeProf> trees) throws Exception {
        ArrayList<RegExTreeProf> result = new ArrayList<RegExTreeProf>();
        boolean found = false;
        RegExTreeProf gauche = null;
        boolean done = false;
        for (RegExTreeProf t: trees) {
            if (!found && t.root==ALTERN && t.subTrees.isEmpty()) {
            if (result.isEmpty()) throw new Exception();
            found = true;
            gauche = result.remove(result.size()-1);
            continue;
            }
            if (found && !done) {
            if (gauche==null) throw new Exception();
            done=true;
            ArrayList<RegExTreeProf> subTrees = new ArrayList<RegExTreeProf>();
            subTrees.add(gauche);
            subTrees.add(t);
            result.add(new RegExTreeProf(ALTERN, subTrees));
            } else {
            result.add(t);
            }
        }
        return result;
    }
    private static RegExTreeProf removeProtection(RegExTreeProf tree) throws Exception {
        if (tree.root==PROTECTION && tree.subTrees.size()!=1) throw new Exception();
        if (tree.subTrees.isEmpty()) return tree;
        if (tree.root==PROTECTION) return removeProtection(tree.subTrees.get(0));

        ArrayList<RegExTreeProf> subTrees = new ArrayList<RegExTreeProf>();
        for (RegExTreeProf t: tree.subTrees) subTrees.add(removeProtection(t));
        return new RegExTreeProf(tree.root, subTrees);
    }

    //EXAMPLE
    // --> RegEx from Aho-Ullman book Chap.10 Example 10.25
    private static RegExTreeProf exampleAhoUllman() {
        RegExTreeProf a = new RegExTreeProf((int)'a', new ArrayList<RegExTreeProf>());
        RegExTreeProf b = new RegExTreeProf((int)'b', new ArrayList<RegExTreeProf>());
        RegExTreeProf c = new RegExTreeProf((int)'c', new ArrayList<RegExTreeProf>());
        ArrayList<RegExTreeProf> subTrees = new ArrayList<RegExTreeProf>();
        subTrees.add(c);
        RegExTreeProf cEtoile = new RegExTreeProf(ETOILE, subTrees);
        subTrees = new ArrayList<RegExTreeProf>();
        subTrees.add(b);
        subTrees.add(cEtoile);
        RegExTreeProf dotBCEtoile = new RegExTreeProf(CONCAT, subTrees);
        subTrees = new ArrayList<RegExTreeProf>();
        subTrees.add(a);
        subTrees.add(dotBCEtoile);
        return new RegExTreeProf(ALTERN, subTrees);
    }

    //FROM TREE TO PARENTHESIS
    public String toString() {
        if (subTrees.isEmpty()) return rootToString();
        String result = rootToString()+"("+subTrees.get(0).toString();
        for (int i=1;i<subTrees.size();i++) result+=","+subTrees.get(i).toString();
        return result+")";
    }
    private String rootToString() {
        if (root==RegExTreeProf.CONCAT) return ".";
        if (root==RegExTreeProf.ETOILE) return "*";
        if (root==RegExTreeProf.ALTERN) return "|";
        if (root==RegExTreeProf.DOT) return ".";
        return Character.toString((char)root);
    }
}