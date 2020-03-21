using System;
using System.ComponentModel;
using System.Windows.Forms;
using System.IO;

namespace WindowsFormsApp1
{
    public partial class Form1 : Form
    {
        private string filename;
        private string extension;
        private string content;

        public Form1()
        {
            InitializeComponent();
        }

        private void openFileDialog1_FileOk(object sender, CancelEventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void folderBrowserDialog1_HelpRequest(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            filename = textBox1.Text;

            // If no file name inside the text box, then open the dialog
            if (filename == "")
            {
                openFileDialog1.Filter = "Shado Object files (.shado, .son)|*.SHADO;*.SON";
                openFileDialog1.ShowDialog();
                openFileDialog1.InitialDirectory = @"C:\Users\shadi\Desktop\WindowsFormsApp1\WindowsFormsApp1";
                filename = openFileDialog1.FileName;
            }

            if (openFileDialog1.ShowDialog() == DialogResult.OK)
                extension = Path.GetExtension(filename);

            // Read the select file
            try
            {
                content = System.IO.File.ReadAllText(@filename);
                status.Text = "File opened successfully!";
                richTextBox1.Text = content;
            }
            catch (System.IO.FileNotFoundException err)
            {
                status.Text = "Cannot open file! File not found!";
            }
        }

        private void richTextBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            button1.Text = "Read";
        }
    }
}
