(ns jante.construct)

(defn new-bot
  []
  {
    :messages '()
    :plugin-states {} 
   })

(defn update-plugin
  [state plugin func]
  (update-in state [:plugin-states plugin] func))
