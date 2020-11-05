import re
import sys

# utility functions


def flattenList(l):
    flat_list = []
    for sublist in l:

        if (isinstance(sublist, str)):
            flat_list.append(sublist)
            continue

        for item in sublist:
            flat_list.append(item)
    return flat_list


# get file name
while True:
    filename = input("Enter the name of the file you want to compile > ")

    if filename.find(".shado.python") == -1:
        print("Your file must contain '.shado.python' extension")
    else:
        break

 # open file
with open(filename, 'r') as file:
    data = file.read()

# run preprocessor
data = data.split("\n")

linenumber = 0
while linenumber < len(data):
    if data[linenumber].startswith("#if"):
        condition = eval(data[linenumber].split()[1])

        data[linenumber] = ""  # delete the #if

        if not condition:
            while not data[linenumber].startswith("#endif"):
                data[linenumber] = ""
                linenumber += 1

        data[linenumber] = ""  # delete the #endif

    elif (data[linenumber].startswith("#include")):
        include_filename = data[linenumber].split(maxsplit=1)[1]
        include_filename = re.sub(r"\"", "", include_filename)

        with open(include_filename, 'r') as module:
            content = module.read()

        data[linenumber] = content.split("\n")
        data = flattenList(data)
        linenumber = 0

    else:
        linenumber += 1

data = "\n".join(data)

# print(data)

# replace stuff
data = re.sub(r"\b;|;\n|;$", "\n", data)
data = re.sub(r"\s+\{|\)\{", ":", data)

toReplace = "var|let|const|auto"
data = re.sub(r'var\s|const\s|auto\s|let\s', "", data)

data = re.sub(r"\bthrow\b", "raise", data)
data = re.sub(r"\bnew\b", "", data)
data = re.sub(r"}[\s+^\n]|}$", "", data)

exec(data)


# def main():
#     # print command line arguments
#     for arg in sys.argv[1:]:
#         print arg


# if __name__ == "__main__":
#     main()

input()
