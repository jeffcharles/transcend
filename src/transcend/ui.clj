(ns transcend.ui)

(defn create-table-model
  []
  (proxy [javax.swing.table.DefaultTableModel] [(java.util.Vector. ["" "A" "B" "C" "D" "E"]) 5]
    (getValueAt [row column]
      (if (== 0 column) (inc row) (proxy-super getValueAt row column)))))
