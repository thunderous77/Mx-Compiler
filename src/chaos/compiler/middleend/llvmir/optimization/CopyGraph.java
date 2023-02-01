package chaos.compiler.middleend.llvmir.optimization;

import chaos.compiler.middleend.llvmir.IRValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CopyGraph {
    public static class CopyEdge {
        public IRValue dest, source;

        public CopyEdge(IRValue dest, IRValue source) {
            this.dest = dest;
            this.source = source;
        }

        public boolean isLoop() {
            return dest.equals(source);
        }

    }

    public final ArrayList<CopyEdge> edges = new ArrayList<>();
    private final HashMap<IRValue, Integer> inDegree = new HashMap<>();

    public CopyGraph() {}

    public void insert(CopyEdge copy) {
        edges.add(copy);
        if (!inDegree.containsKey(copy.source)) inDegree.put(copy.source, 1);
        else inDegree.put(copy.source, inDegree.get(copy.source) + 1);
    }

    // remove from copyList by iterator to avoid concurrent
    public void remove(CopyEdge copy, Iterator<CopyEdge> it) {
        it.remove();
        if (inDegree.get(copy.source) == 1) inDegree.remove(copy.source);
        else inDegree.put(copy.source, inDegree.get(copy.source) - 1);
    }

    public boolean isFree(IRValue dest) {
        return !inDegree.containsKey(dest);
    }

}
