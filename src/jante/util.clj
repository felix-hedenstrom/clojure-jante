(ns jante.util)

(defn nil-if-empty
  [arg]
  (if (empty? arg)
    nil
    arg))
