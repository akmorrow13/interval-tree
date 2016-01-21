/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.akmorrow13.intervaltree

import collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag
import org.bdgenomics.adam.models.Interval

class Node[K <: Interval, V: ClassTag](int: K) extends Serializable {
  val interval: K = int
  var leftChild: Node[K, V] = null
  var rightChild: Node[K, V] = null
  var subtreeMax = int.end


  // DATA SHOULD BE STORED MORE EFFICIENTLY
  var data: ListBuffer[V] = new ListBuffer()

  def this(int: K, t: V) = {
    this(int)
    put(t)
  }

  def getSize(): Long = {
    data.length
  }

  override def clone: Node[K, V] = {
    val n: Node[K, V] = new Node(interval)
    n.data = data
    n
  }

  def clearChildren() = {
    leftChild = null
    rightChild = null
  }

  def multiput(rs: Iterator[V]) = {
    val newData = rs.toList
    data ++= newData
  }

  def put(newData: V) = {
    data += newData
  }

  def get(): Iterator[V] = data.toIterator

  def greaterThan(other: K): Boolean = {
      interval.start > other.start
  }

  def equals(other: K): Boolean = {
      (interval.start == other.start && interval.end == other.end)
  }

  def lessThan(other: K): Boolean = {
      interval.start < other.start
  }

  def overlaps(other: K): Boolean = {
    interval.start <= other.end && interval.end >= other.start
  }

}
