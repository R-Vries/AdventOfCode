class Map:
    """This class represents a two-dimensional map"""

    def __init__(self, lists):
        self.lists = lists

    def __repr__(self):
        return self.lists

    def __str__(self):
        return "\n".join(''.join(p for p in row) for row in self.lists)

    def __index__(self, element):
        for i, row in enumerate(self.lists):
            if element in row:
                return i, row.index('@')

    def __getitem__(self, point):
        return self.lists[point[0]][point[1]]

    def __setitem__(self, point, value):
        self.lists[point[0]][point[1]] = value

    def get_lists(self):
        return self.lists

    def index(self, element):
        return self.__index__(element)
