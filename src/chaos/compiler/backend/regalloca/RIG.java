package chaos.compiler.backend.regalloca;

// Register Interference Graph


import chaos.compiler.backend.asm.inst.AsmMvInst;
import chaos.compiler.backend.asm.operand.AsmRegister;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RIG {

    public static int INF = 1145141919;

    public static class Edge {
        public AsmRegister u, v;

        public Edge(AsmRegister u, AsmRegister v) {
            this.u = u;
            this.v = v;
        }

        public boolean isLoop() {
            return this.u == this.v;
        }

        public Edge convert() {
            return new Edge(v, u);
        }

        @Override
        public int hashCode() {
            return u.hashCode() ^ v.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Edge && (((Edge) o).u == u && ((Edge) o).v == v);
        }
    }

    public static class Node {
        // 邻结点
        public HashSet<AsmRegister> adjacentNodeList = new LinkedHashSet<>();
        public boolean precolored;
        // 度数（邻结点个数）
        public int degree;
        public double priority;
        public Set<AsmMvInst> moveList = new LinkedHashSet<>();

        public void init(boolean isPrecolored) {
            this.adjacentNodeList.clear();
            this.moveList.clear();
            this.priority = 0;
            if (isPrecolored) this.degree = INF;
            else this.degree = 0;
            this.precolored = isPrecolored;
        }
    }

    public Set<Edge> edgeSet = new LinkedHashSet<>();

    public void addEdge(RIG.Edge edge) {
        if (!edgeSet.contains(edge) && !(edge.u == edge.v)) {
            edgeSet.add(edge);
            edgeSet.add(edge.convert());
            if (!edge.u.node.precolored) {
                edge.u.node.adjacentNodeList.add(edge.v);
                edge.u.node.degree++;
            }
            if (!edge.v.node.precolored) {
                edge.v.node.adjacentNodeList.add(edge.u);
                edge.v.node.degree++;
            }
        }
    }

    public void init() {
        edgeSet.clear();
    }

}
