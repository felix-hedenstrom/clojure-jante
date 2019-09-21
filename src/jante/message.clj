(ns jante.message)

(def line "-------------------------------")

(defn print-message
  [message]
  (println line)
  (println "Sender: " (get message :sender))
  (println "Recipient: " (get message :recipient))
  (println "Text: " (get message :text))
  (println line))

