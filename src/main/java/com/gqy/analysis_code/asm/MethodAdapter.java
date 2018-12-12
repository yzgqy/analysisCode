package com.gqy.analysis_code.asm;

import java.util.HashMap;

import com.gqy.analysis_code.dao.ClassnodeMapper;
import com.gqy.analysis_code.dao.EdgeMapper;
import com.gqy.analysis_code.entity.Classnode;
import com.gqy.analysis_code.entity.Edge;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.beans.factory.annotation.Autowired;

public class MethodAdapter  extends MethodVisitor implements Opcodes {
    @Autowired
    private ClassnodeMapper cm;
    @Autowired
    private EdgeMapper em;
    protected String className = null;
    protected int access = -1;
    protected String name = null;
    protected String desc = null;
    protected String signature = null;
    protected String[] exceptions = null;

    public static HashMap<String, Edge> edges = new HashMap<String, Edge>();

    public MethodAdapter(final MethodVisitor mv, final String className, final int access, final String name,
                         final String desc, final String signature, final String[] exceptions,
                         HashMap<String, Classnode> classnoedes) {
        // super(ASM5, mv, access, name, desc);
        super(ASM6, mv);
        this.className = className;
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.signature = signature;
        this.exceptions = exceptions;
        // this.classnoedes = classnoedes;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        System.out.println(this.className + " " + this.name + "   call  " + owner + " " + name);
        if (mv != null) {
            mv.visitMethodInsn(opcode, owner, name, desc, itf);
        }

        if (!owner.equals(this.className)) {
            // Classnode sourcenode = classnoedes.get(this.className);
            // Classnode targetnode = classnoedes.get(owner);
            String edgeKey = this.className + "_" + owner;
            Edge myedge = edges.get(edgeKey);
            if (myedge != null) {
                int weight = myedge.getWeight();
                myedge.setWeight(weight + 1);
                edges.put(edgeKey, myedge);
            } else {
                Edge newedge = new Edge();
                newedge.setSource(this.className);
                newedge.setTarget(owner);
                newedge.setWeight(1);
                edges.put(edgeKey, newedge);
            }
        }
    }
}