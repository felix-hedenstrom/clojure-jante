(ns jante.event
  (:require [jante.plugin-namespaces]
            [jante.construct :refer [set-plugin-state get-plugin-state add-messages]]
            [clojure.test :refer [is]]))

(defn get-functions-in-namespace
  {:test
   (fn []
     (is (as-> (get-functions-in-namespace 'jante.event) func
           (map first func)
           (some #(= % 'get-functions-in-namespace) func))
         "This function should be contained within this namespace"))}
  [jante-namespace]
  (into [] (ns-publics jante-namespace)))

(defn get-all-triggered-by
  {:test
   (fn []
     (is (-> (get-all-triggered-by :on-message nil [[:event 'jante.event]])
             (empty?))
         "There are no functions triggered by :on-message in this namespace"))}
  ([event data]
   (get-all-triggered-by event data jante.plugin-namespaces/namespaces))
  ([event data search-namespace]
   (reduce
    (fn
      filter-plugins
      [known-triggers [plugin-key plugin-namespace]]
      (->> (get-functions-in-namespace plugin-namespace)
           (map second)
           (filter
            (fn
              is-triggered
              [f]
              (if-let [trigger-filter
                       (get-in (meta f) [:events event])]
                (trigger-filter data)
                false)))
           (map #(list plugin-key %))
           (concat known-triggers)))
    '()
    search-namespace)))

(defn trigger-event
  [state event data]
  (loop [state state
         triggered (get-all-triggered-by event data)]
    (if (empty? triggered)
      state
      (let [[plugin func] (first triggered)]
        (let [[plugin-state messages] (func (get-plugin-state state plugin) data)]
          (recur
           (-> (set-plugin-state state plugin plugin-state)
               (add-messages messages))
           (rest triggered)))))))
