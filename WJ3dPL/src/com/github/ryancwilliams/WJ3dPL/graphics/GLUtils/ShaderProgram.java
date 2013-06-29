/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ryancwilliams.WJ3dPL.graphics.GLUtils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

/**
 * 
 * @author ryancwilliams
 */
public class ShaderProgram {
    
    public final int program;
    
    /**
     * Creates a new shader program with the provided vertex and fragment 
     * shader source code.
     * The provided attributes are linked to this program.
     * @param vertexShaderSource the source code of the vertex shader.
     * @param fragmentShaderSource the source code of the fragment shader.
     * @param attributes The Vertex Attributes to bind to this shader program.
     * @throws LWJGLException If their is a issue compiling the shaders or 
     * creating or binding the program.  
     */
    public ShaderProgram(String vertexShaderSource, String fragmentShaderSource, 
            List<VertexAttribute> attributes) throws LWJGLException {
        //Check if any of the sourcecode paramaters are null
        if(fragmentShaderSource == null || fragmentShaderSource == null) {
            //If any of the sourcecode paramaters were null
            //throw a exception
            throw new IllegalArgumentException("Shader source may not be null");
        }
        //Check if shaders are not supported
        if(!ShaderProgram.isSupported()) {
            //If shaders are not supported
            //throw a exception
            throw new LWJGLException("Shaders are not supported on this device");
        }
        
        //Compile the shaders
        int vertexShader = ShaderProgram
                .compileShader(GL20.GL_VERTEX_SHADER, vertexShaderSource);
        int fragmentShader = ShaderProgram
                .compileShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderSource);
        
        //Create the program
        this.program = GL20.glCreateProgram();
        
        //Bind the attrib locations
        //Check if attributes were provided
        if (attributes != null) {
            //For each attribute
            for(VertexAttribute attribute : attributes) {
                //Check if the attribute is not null
                if(attribute != null) {
                    //bind the attribute
                    GL20.glBindAttribLocation(this.program, attribute.index, attribute.name);
                }
            }
        }
        
        //Attach the shaders
        GL20.glAttachShader(this.program, vertexShader);
        GL20.glAttachShader(this.program, fragmentShader);
        
        //Link the program
        GL20.glLinkProgram(this.program);
        
        //Get if the program link was good
        boolean programLink = GL20.glGetProgrami(this.program, GL20.GL_LINK_STATUS) == GL11.GL_TRUE;
        
        //Get the log
        String infoLog = GL20.glGetProgramInfoLog(this.program, 
                GL20.glGetProgrami(this.program, GL20.GL_INFO_LOG_LENGTH));
        
        //Log the log if a log is present
        if (infoLog != null && infoLog.trim().length() != 0) {
            Logger.getLogger(ShaderProgram.class.getName()).log(Level.FINEST, infoLog);
        }
        
        //Check if program link is bad
        if (programLink == false) {
            throw new LWJGLException("Failure in linking program. Error log:\n" + infoLog);
        }
        
        //detach and delete the shaders which are no longer needed
        GL20.glDetachShader(this.program, vertexShader);
        GL20.glDetachShader(this.program, fragmentShader);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }
    
    /**
     * Checks if vertex and fragment shaders are supported.
     * @return true if vertex and fragment shaders are supported.
     */
    public static boolean isSupported() {
        // Get Capabilities
        ContextCapabilities capabilities = GLContext.getCapabilities();
        return capabilities.GL_ARB_shader_objects 
                && capabilities.GL_ARB_vertex_shader
                && capabilities.GL_ARB_fragment_shader;
    }
    
    /**
     * Compiles a shader from the provided source and returns its OpenGL handle.
     * @param type the shader type to use when compiling.
     * @param source the source to compile.
     * @return the OpenGL handle for this shader.
     * @throws LWJGLException if compilation was unsuccessful
     */
    public static int compileShader(int type, String source) throws LWJGLException {
        //Create the shader ponter varable
        int shader;
        //Create the shader
        shader = GL20.glCreateShader(type);
        
        //load the source
        GL20.glShaderSource(shader, source);
        //compile the source
        GL20.glCompileShader(shader);
        
        //Get if the compile was good
        boolean compile = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_TRUE;
        
        //Get the log
        String infoLog = GL20.glGetShaderInfoLog(shader, 
                GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH));
        
        //Log the log if a log is present
        if (infoLog != null && infoLog.trim().length() != 0) {
            Logger.getLogger(ShaderProgram.class.getName()).log(Level.FINEST, infoLog);
        }
        
        //Check if the compiling was unsuccessful
        if(compile == false) {
            //throw a exception if unsuccessful
            throw new LWJGLException("Failure in compiling " 
                    + ShaderProgram.typeToString(type) 
                    + ". Error log:\n" + infoLog);
        }
        
        //Return the OpenGL pointer for the shader
        return shader;
    }
    
    /**
     * Converts the provided shader type value to a string.
     * @param type the type value to convert.
     * @return the type value as a String.
     */
    public static String typeToString(int type) {
        switch (type) {
            case GL20.GL_VERTEX_SHADER: 
                return "GL_VERTEX_SHADER";
            case GL20.GL_FRAGMENT_SHADER:
                return "GL_FRAGMENT_SHADER";
            default: 
                return "shader";
        }
    }
    
    /**
     * Make this shader the active program.
     */
    public void use() {
        GL20.glUseProgram(this.program);
    }
    
    /**
     * Destroy this shader program. 
     * Frees resources used by this program.
     */
    public void destroy() {
        //Flag this program for deletion. Will be deleated when no longer in use. 
        GL20.glDeleteProgram(this.program);
    }
    
    
}
