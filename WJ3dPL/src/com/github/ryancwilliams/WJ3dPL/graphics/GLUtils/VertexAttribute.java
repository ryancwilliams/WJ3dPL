/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ryancwilliams.WJ3dPL.graphics.GLUtils;

/**
 * A Vertex Attribute is a per-vertex value passed to the Vertex Shader.
 * @author ryancwilliams
 */
public class VertexAttribute {

    /**
     * The index of this generic vertex attribute. 
     * Is also the index that this generic vertex attribute should be bound to. 
     */
    public final int index;
    /**
     * The name of this generic vertex attribute.
     */
    public final String name;

    /**
     * Creates a VertexAttribute object
     * @param index The index that this generic vertex attribute should be 
     * bound to.
     * @param name The name of this generic vertex attribute.
     */
    public VertexAttribute(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
