(ns transcend.main
  (:use seesaw.core
        transcend.ui))

(defn -main
  [& args]
  (invoke-later
    (-> (frame :title "Transcend"
          :content (scrollable (table
                     :model (create-table-model)
                     :show-grid? true))
          :on-close :exit)
      pack!
      show!)))
