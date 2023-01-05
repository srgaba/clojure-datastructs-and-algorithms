(ns run.test
  (:use [clojure pprint]))

(defn build-stack
  []
  (atom []))

(defn add!
  [data stack]
  (swap! stack conj data))

(defn rm!
  [stack]
  (swap! stack drop-last))

(defn init
  []
  (let [stack (build-stack)]
    (add! "foo" stack)
    (add! "bar" stack)
    (rm! stack)
    (println @stack)))

(init)
