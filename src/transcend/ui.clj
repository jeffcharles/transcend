(ns transcend.ui)

(declare create-table-model)
(declare create-table-cell-renderer)

(defn create-table
  []
  (let [table (javax.swing.JTable. (create-table-model))]
    (doto table
      (.setDefaultRenderer (class Object) (create-table-cell-renderer)))))

(defn- create-table-model
  []
  (proxy [javax.swing.table.DefaultTableModel] [(java.util.Vector. ["" "A" "B" "C" "D" "E"]) 5]
    (getValueAt [row column]
      (if (== 0 column) (inc row) (proxy-super getValueAt row column)))
    (getColumnClass [columnIndex]
      (class Object))))

(defn- create-table-cell-renderer
  []
  (proxy [javax.swing.table.DefaultTableCellRenderer] []
    (getTableCellRendererComponent [table value isSelected hasFocus row col]
      (let [component (proxy-super getTableCellRendererComponent table value isSelected hasFocus row col)]
        (doto component
          (.setBackground (if (= 0 col) java.awt.Color/GRAY java.awt.Color/WHITE)))
        component))))
