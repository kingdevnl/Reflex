package org.magmafoundation.reflex.utils

import org.gradle.internal.impldep.org.bouncycastle.asn1.x500.style.RFC4519Style.cn
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.zip.ZipOutputStream


object AccessModder {

    public fun transformJar(inFile: File, outFile: File) {
        val inJar = JarFile(inFile)

        /**
         * Transform the access for all protected/package-private entities to public.
         */
        fun checkAccess(cn: ClassNode) {
            cn.access = acc(cn.access)
            for (field in cn.fields) {
                field.access = acc(field.access)
            }
            for (method in cn.methods) {
                if (method.name.contains("<")) {
                    continue
                }
                method.access = acc(method.access)
            }
        }

        //  Get classes
        val files: List<Pair<String, ByteArray>> = inJar.entries().toList()
            .filter { !it.name.contains("META-INF") }
            .map {
                val inputStream = inJar.getInputStream(it)
                val bytes = inputStream.readBytes()
                inputStream.close()
                it.name to if (it.name.endsWith(".class") && it.name.contains("net/minecraft")) {
                    val classNode = ClassNode()
                    val classReader = ClassReader(bytes)
                    classReader.accept(classNode, 0)
                    val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
                    // transform classes
                    checkAccess(classNode)
                    classNode.accept(classWriter)
                    classWriter.toByteArray()
                } else bytes
            }.toList()
        inJar.close()

        val outJarStream = ZipOutputStream(FileOutputStream(outFile))
        val output = BufferedOutputStream(outJarStream)

        files.forEach {
            outJarStream.putNextEntry(JarEntry(it.first))
            output.write(it.second)
            output.flush()
            outJarStream.closeEntry()
        }
        output.close()
        outJarStream.close()
    }
    private fun acc(cnaccess: Int): Int {
        var access = Opcodes.ACC_PUBLIC
        if (AccessHelper.isAbstract(cnaccess)) {
            access = access or Opcodes.ACC_ABSTRACT
        }
        if (AccessHelper.isStatic(cnaccess)) {
            access = access or Opcodes.ACC_STATIC
        }
        if (AccessHelper.isEnum(cnaccess)) {
            access = access or Opcodes.ACC_ENUM
        }
        if (AccessHelper.isAnnotation(cnaccess)) {
            access = access or Opcodes.ACC_ANNOTATION
        }
        if (AccessHelper.isInterface(cnaccess)) {
            access = access or Opcodes.ACC_INTERFACE
        }
        if (AccessHelper.isVolatile(cnaccess)) {
            access = access or Opcodes.ACC_VOLATILE
        }
        if (AccessHelper.isVolatile(cnaccess)) {
            access = access or Opcodes.ACC_TRANSIENT
        }
        if (AccessHelper.isSynchronized(cnaccess)) {
            access = access or Opcodes.ACC_SYNCHRONIZED
        }
        if (AccessHelper.isNative(cnaccess)) {
            access = access or Opcodes.ACC_NATIVE
        }
        return access
    }
}