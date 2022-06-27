(ns run.core
  (:require [clojure.string :as str])
  (:use [clojure.pprint]))

(def max-length 50)

(defn build-hash-table
  []
  (let [hash-table (java.util.ArrayList. max-length)]
    (dotimes [_ max-length]
      (.add hash-table nil))
    hash-table))

(defn generate-hash
  [str]
  (let [limit (count str)]
    (loop [index 0
           acm 0]
      (if (= index limit)
        acm
        (recur (inc index) (rem (* (+ acm (.codePointAt str index)) index) max-length))))))

(defn get-or-create-data-set!
  [ht address]
  (if-let [data-set (.get ht address)]
    data-set
    (do
      (.add ht address (atom []))
      (.get ht address))))

(defn add!
  [ht key value]
  (let [address (generate-hash key)
        a (get-or-create-data-set! ht address)]
    (swap! a conj [address value])))

(defn get-
  [ht key]
  (let [address (generate-hash key)
        data-set (get-or-create-data-set! ht address)]
    (for [data @data-set :when (= (get data 0) address)] data)))

(defn go-run []

  (let [ht (build-hash-table)]
    (add! ht "foo" "coolll")
    (println (get- ht "foo"))))

(go-run)