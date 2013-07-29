/*
 * Copyright 2013 ryancwilliams.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
