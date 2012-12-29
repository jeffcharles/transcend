(ns transcend.ui)

(declare create-table-model)
(declare create-table-cell-renderer)

(defn create-table
  []
  (let [table (javax.swing.JTable. (create-table-model))
        first-col (-> (.getColumnModel table) (.getColumn 0))]
    (doto first-col
      (.setPreferredWidth 10)
      (.setResizable false))
    (doto table
      (.setDefaultRenderer java.lang.Object (create-table-cell-renderer))
      (.setGridColor java.awt.Color/LIGHT_GRAY))))

(defn- create-table-model
  []
  (proxy [javax.swing.table.DefaultTableModel] [(java.util.Vector. ["" "A" "B" "C" "D" "E"]) 5]
    (getValueAt [row column]
      (if (== 0 column) (inc row) (proxy-super getValueAt row column)))
    (getColumnClass [columnIndex]
      java.lang.Object)
    (isCellEditable [row column]
      (not= column 0))))

(defn- create-table-cell-renderer
  []
  (proxy [javax.swing.table.DefaultTableCellRenderer] []
    (getTableCellRendererComponent [table value isSelected hasFocus row col]
      (let [component (proxy-super getTableCellRendererComponent table value isSelected hasFocus row col)]
        (doto component
          (.setBackground (if (= 0 col) java.awt.Color/GRAY java.awt.Color/WHITE)))
        component))))
